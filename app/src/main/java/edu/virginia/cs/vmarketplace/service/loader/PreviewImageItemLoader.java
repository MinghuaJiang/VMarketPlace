package edu.virginia.cs.vmarketplace.service.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.util.ImageUtil;


/**
 * Created by cutehuazai on 11/30/17.
 */

public class PreviewImageItemLoader  extends AsyncTaskLoader<List<PreviewImageItem>> {
    private List<String> fileList;
    private int size;
    public PreviewImageItemLoader(Context context, List<String> fileList, int size) {
        super(context);
        this.fileList = fileList;
        this.size = size;
    }

    @Override
    public List<PreviewImageItem> loadInBackground() {
        List<PreviewImageItem> list = new ArrayList<>();
        for(String file: fileList){
            File fileHandle = new File(file);
            if(!fileHandle.exists()){
                list.add(
                        new PreviewImageItem(ImageUtil.decodeSampledBitmapFromResource(getContext().getResources(), R.drawable.place_holder_64p,
                                size, size),
                                file));
            }else {
                list.add(
                        new PreviewImageItem(ImageUtil.decodeSampledBitmapFromFile(file,
                                size, size),
                                file));
            }
        }
        return list;
    }
}
