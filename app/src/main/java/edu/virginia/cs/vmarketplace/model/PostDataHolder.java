package edu.virginia.cs.vmarketplace.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VINCENTWEN on 11/26/17.
 */

public class PostDataHolder {
    private String imageLocation1;
    private List<String> savedImage;

    public PostDataHolder() {
        this.savedImage = new ArrayList<>();
    }

    public String getImageLocation1() {
        return imageLocation1;
    }

    public List<String> getSavedImage() {
        return savedImage;
    }

    public void setImageLocation1(String imageLocation1) {
        this.imageLocation1 = imageLocation1;
    }

    public void setSavedImage(List<String> savedImage) {
        this.savedImage = savedImage;
    }

    private static final PostDataHolder postDataHolder = new PostDataHolder();

    public static PostDataHolder getInstance() {
        return postDataHolder;
    }
}
