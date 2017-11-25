package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class PublishItem {
    private long id;
    private double price;
    private String image;
    private String title;
    private int replyCount;
    private int viewCount;

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
