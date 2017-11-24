package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class SoldItem {
    private long id;
    private double price;
    private String image;
    private int imageResourceId;
    private String title;
    private int messageId;

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
