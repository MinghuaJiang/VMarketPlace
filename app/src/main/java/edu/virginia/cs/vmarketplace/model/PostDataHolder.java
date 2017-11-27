package edu.virginia.cs.vmarketplace.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VINCENTWEN on 11/26/17.
 */

public class PostDataHolder {
    private String curImageLocation;
    private int imageCount;
    private List<String> savedImage;
    private Map<String, Bitmap> bitmapMap;

    private PostDataHolder() {
        this.savedImage = new ArrayList<>();
        this.bitmapMap = new LinkedHashMap<>();
    }

    public String getCurImageLocation() {
        return curImageLocation;
    }

    public List<String> getSavedImage() {
        return savedImage;
    }

    public void setCurImageLocation(String curImageLocation) {
        this.curImageLocation = curImageLocation;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public Map<String, Bitmap> getBitmapMap() {
        return bitmapMap;
    }

    private static final PostDataHolder postDataHolder = new PostDataHolder();

    public static PostDataHolder getInstance() {
        return postDataHolder;
    }
}
