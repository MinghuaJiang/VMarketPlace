package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.view.PhotoActivity;
import edu.virginia.cs.vmarketplace.view.PublishFormActivity;

/**
 * Created by cutehuazai on 11/30/17.
 */

public class ImageViewAdapter extends ArrayAdapter<String> {
    private List<String> mFiles;
    private String baseDir;
    private AppContext appContext;

    public ImageViewAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
        baseDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator;
        appContext = AppContextManager.getContextManager().getAppContext();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        listView = LayoutInflater.from(getContext()).inflate(R.layout.publish_thumb_item, parent
                , false);
        ImageView imageView = listView.findViewById(R.id.image);
        ImageView imageCloseView = listView.findViewById(R.id.image_close);
        if(position != mFiles.size()) {
            final String currentItem = getItem(position);

            File file = new File(baseDir + currentItem);
            if(file.exists()){
                Picasso.with(getContext()).load(file).placeholder(R.drawable.product_placeholder_96dp).
                        fit().into(imageView);
            }else{
                ProductItemsDO item = appContext.getItemsDO();
                S3Service.getInstance(getContext()).download(item.getPics().get(position), item.getOriginalFiles().get(position)
                        , (x)->
                                Picasso.with(getContext()).load(x.get(0)).placeholder(R.drawable.product_placeholder_96dp).
                                        fit().into(imageView));
            }

            imageCloseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    file.delete();
                    mFiles.remove(position);
                    ImageViewAdapter.this.remove(currentItem);
                }
            });
        }else{
            imageCloseView.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getContext() instanceof PublishFormActivity){
                        ((PublishFormActivity) getContext()).saveState();
                    }
                    Intent intent = new Intent(getContext(), PhotoActivity.class);
                    List<String> fileList = mFiles.stream().map(
                            (x)->
                            {return baseDir + x;
                            }).collect(Collectors.toList());
                    intent.putStringArrayListExtra(AppConstant.FILE_LIST, new ArrayList<String>(fileList));
                    getContext().startActivity(intent);
                }
            });
            if(mFiles.size() == 10){
                imageView.setVisibility(View.GONE);
            }else{
                imageView.setVisibility(View.VISIBLE);
            }
        }
        return listView;
    }

    public void setmFiles(List<String> mFiles) {
        this.mFiles = mFiles;
    }
}
