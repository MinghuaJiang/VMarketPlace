package edu.virginia.cs.vmarketplace.model;

import android.graphics.Bitmap;

/**
 * Created by cutehuazai on 11/30/17.
 */

public class PreviewImageItem{
    private Bitmap thumbImage;
    private String fileName;

    public PreviewImageItem(Bitmap thumbImage, String fileName){
        this.thumbImage = thumbImage;
        this.fileName = fileName;
    }

    public Bitmap getThumbImage() {
        return thumbImage;
    }

    public String getFileName() {
        return fileName;
    }

}
