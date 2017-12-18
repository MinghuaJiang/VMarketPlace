package edu.virginia.cs.vmarketplace.view.adapter.model;

/**
 * Created by cutehuazai on 12/14/17.
 */

public class ImageGalleryItem {
    private String originalFile;
    private String s3URL;

    public ImageGalleryItem(String originalFile, String s3URL){
        this.originalFile = originalFile;
        this.s3URL = s3URL;
    }

    public String getOriginalFile() {
        return originalFile;
    }

    public String getS3URL() {
        return s3URL;
    }
}
