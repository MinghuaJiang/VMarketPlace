package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.view.PhotoActivity;
import edu.virginia.cs.vmarketplace.view.PublishFormActivity;

/**
 * Created by cutehuazai on 11/30/17.
 */

public class ImageViewAdapter extends ArrayAdapter<PreviewImageItem> {
    private List<String> mFiles;
    public ImageViewAdapter(@NonNull Context context, @NonNull List<PreviewImageItem> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(position != mFiles.size()) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.publish_thumb_item, parent
                        , false);

            final PreviewImageItem currentItem = getItem(position);

            ImageView imageView = listView.findViewById(R.id.image);
            imageView.setImageBitmap(currentItem.getThumbImage());

            ImageView imageCloseView = listView.findViewById(R.id.image_close);
            imageCloseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new File(mFiles.get(position)).delete();
                    mFiles.remove(position);
                    ImageViewAdapter.this.remove(currentItem);
                }
            });
        }else{
            listView = LayoutInflater.from(getContext()).inflate(R.layout.image_add, parent
                        , false);
            ImageView view = listView.findViewById(R.id.add_image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getContext() instanceof PublishFormActivity){
                        ((PublishFormActivity) getContext()).saveState();
                    }
                    Intent intent = new Intent(getContext(), PhotoActivity.class);
                    intent.putStringArrayListExtra(AppConstant.FILE_LIST, new ArrayList<String>(mFiles));
                    getContext().startActivity(intent);
                }
            });
            if(mFiles.size() == 10){
                listView.setVisibility(View.INVISIBLE);
            }else{
                listView.setVisibility(View.VISIBLE);
            }
        }
        return listView;
    }

    public void setmFiles(List<String> mFiles) {
        this.mFiles = mFiles;
    }
}
