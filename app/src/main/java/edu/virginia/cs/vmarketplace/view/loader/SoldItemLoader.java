package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.SoldItem;

/**
 * Created by cutehuazai on 11/29/17.
 */

public class SoldItemLoader extends AsyncTaskLoader<List<SoldItem>> {
    public SoldItemLoader(Context context) {
        super(context);
    }

    @Override
    public List<SoldItem> loadInBackground() {
        List<SoldItem> list = new ArrayList<SoldItem>();
        SoldItem item = new SoldItem();
        item.setId(1);
        item.setTitle("Cool Kid");
        item.setPrice(100.0);
        item.setProductType("Ride");
        list.add(item);
        item = new SoldItem();
        item.setId(2);
        item.setTitle("Bao");
        item.setPrice(200.0);
        item.setProductType("Second Hand");
        item.setImage("https://s3.amazonaws.com/vmarketplace/product/bag.png");
        list.add(item);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
