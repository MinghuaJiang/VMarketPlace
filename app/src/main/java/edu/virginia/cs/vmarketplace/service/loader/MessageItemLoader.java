package edu.virginia.cs.vmarketplace.service.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.MessageItem;

/**
 * Created by cutehuazai on 11/29/17.
 */

public class MessageItemLoader extends AsyncTaskLoader<List<MessageItem>> {

    public MessageItemLoader(Context context) {
        super(context);
    }

    @Override
    public List<MessageItem> loadInBackground() {
        List<MessageItem> result = new ArrayList<MessageItem>();
        MessageItem item = new MessageItem();
        item.setSellerName("Ben");
        item.setThumbPic("https://s3.amazonaws.com/vmarketplace/product/bag.png");
        item.setSellerPic("https://s3.amazonaws.com/vmarketplace/profile/index.png");
        item.setLatestMessage("test");
        item.setLatestUpdateTime("2017-11-02");
        result.add(item);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
