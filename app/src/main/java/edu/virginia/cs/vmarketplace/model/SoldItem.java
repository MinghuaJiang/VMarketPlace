package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class SoldItem {
    private long id;
    private String productType;
    private double price;
    private String image;
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

    public String getTitle() {
        return title;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public void setTitle(String title) {
        this.title = title;
    }

}
