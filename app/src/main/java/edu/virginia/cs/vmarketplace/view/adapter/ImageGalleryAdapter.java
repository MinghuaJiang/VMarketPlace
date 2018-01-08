package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.view.adapter.model.ImageGalleryItem;

/**
 * Created by cutehuazai on 12/14/17.
 */

public class ImageGalleryAdapter extends RefreshableRecycleAdapter<ImageGalleryItem, ImageGalleryAdapter.MyViewHolder> {
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public ImageGalleryAdapter(Context context, List<ImageGalleryItem> items){
        super(context, items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.gallery_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageGalleryItem item = getItems().get(position);

        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator +
                item.getOriginalFile());
        if(file.exists()){
            Picasso.with(getContext()).load(file).placeholder(R.drawable.product_placeholder_96dp).
                    fit().into(holder.imageView);
        }else{
            S3Service.getInstance(getContext()).download(item.getS3URL(), item.getOriginalFile()
                    , (x)->
                            Picasso.with(getContext()).load(x.get(0)).placeholder(R.drawable.product_placeholder_96dp).
                                    fit().into(holder.imageView));
        }
        holder.imageView.setOnClickListener(listener);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
