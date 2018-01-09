package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 1/8/18.
 */

public class CameraGalleryAdapter extends RefreshableRecycleAdapter<String, CameraGalleryAdapter.MyViewHolder> {
    private View.OnClickListener listener;
    private int pos;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public CameraGalleryAdapter(Context context, List<String> items){
        super(context, items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.camera_gallery_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String item = getItems().get(position);

        Picasso.with(getContext()).load(new File(item)).placeholder(R.drawable.product_placeholder_96dp).
                    fit().into(holder.imageView);

        if(listener != null) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CameraGalleryAdapter.this.pos = position;
                    listener.onClick(v);
                }
            });
            holder.imageViewClose.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    CameraGalleryAdapter.this.removeData(position);
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageView imageViewClose;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            imageViewClose = itemView.findViewById(R.id.image_close);
        }
    }

    public int getPosition() {
        return pos;
    }
}
