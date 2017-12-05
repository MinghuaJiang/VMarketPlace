package edu.virginia.cs.vmarketplace.model;

import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class PublishItem {
    private ProductItemsDO itemsDO;
    public PublishItem(ProductItemsDO itemsDO){
        this.itemsDO = itemsDO;
    }

    public String getProductType() {
        return itemsDO.getCategory() + " - " + itemsDO.getSubcategory();
    }

    public String getId() {
        return itemsDO.getItemId();
    }

    public double getPrice() {
        return itemsDO.getPrice();
    }

    public String getImage() {
        return itemsDO.getThumbPic();
    }

    public String getTitle() {
        return itemsDO.getTitle();
    }

    public int getReplyCount() {
        return itemsDO.getReplyCount().intValue();
    }

    public int getViewCount() {
        return itemsDO.getViewCount().intValue();
    }

    public ProductItemsDO getItemsDO() {
        return itemsDO;
    }
}
