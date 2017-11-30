package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.PublishItem;

/**
 * Created by cutehuazai on 11/29/17.
 */

public class PublishItemLoader extends AsyncTaskLoader<List<PublishItem>> {
    public PublishItemLoader(Context context) {
        super(context);
    }

    @Override
    public List<PublishItem> loadInBackground() {
        List<PublishItem> list = new ArrayList<PublishItem>();
        PublishItem item = new PublishItem();
        item.setId(1);
        item.setTitle("Women HandBag");
        item.setPrice(100.0);
        item.setReplyCount(5);
        item.setViewCount(200);
        item.setProductType("Second Hand");
        item.setImage("https://s3.amazonaws.com/vmarketplace/product/bag.png");
        list.add(item);
        item = new PublishItem();
        item.setId(2);
        item.setTitle("A Ride to IAD");
        item.setPrice(200.0);
        item.setReplyCount(3);
        item.setViewCount(100);
        item.setProductType("Ride");
        list.add(item);

        item = new PublishItem();
        item.setId(3);
        item.setTitle("Apartment Sublease");
        item.setPrice(500.0);
        item.setReplyCount(3);
        item.setViewCount(100);
        item.setProductType("Sublease");
        item.setImage("https://s3.amazonaws.com/vmarketplace/product/Apartment.png");
        list.add(item);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
