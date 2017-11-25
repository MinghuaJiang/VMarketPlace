package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.MessageItem;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class MessageFragment extends AbstractFragment{
    public MessageFragment(){
        super("message", R.drawable.ic_message_24dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message, container, false);
        ListView listView = rootView.findViewById(R.id.message_detail_list);
        MessageItemAdapter adapter = new MessageItemAdapter(getActivity(), getMessageItemList());
        listView.setAdapter(adapter);

        return rootView;
    }

    private List<MessageItem> getMessageItemList(){
        List<MessageItem> result = new ArrayList<MessageItem>();
        MessageItem item = new MessageItem();
        item.setSellerName("Ben");
        item.setThumbPic("https://s3.amazonaws.com/vmarketplace/product/bag.png");
        item.setSellerPic("https://s3.amazonaws.com/vmarketplace/profile/index.png");
        item.setLatestMessage("test");
        item.setLatestUpdateTime("2017-11-02");
        result.add(item);
        return result;

    }
}
