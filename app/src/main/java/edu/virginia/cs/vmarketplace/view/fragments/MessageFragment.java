package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.MessageItem;
import edu.virginia.cs.vmarketplace.view.MessageDetailActivity;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class MessageFragment extends AbstractFragment{
    public MessageFragment(){
        super("message", R.drawable.message_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message, container, false);
        ListView listView = rootView.findViewById(R.id.message_detail_list);
        final List<MessageItem> list = getMessageItemList();
        MessageItemAdapter adapter = new MessageItemAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageItem item = list.get(position);
                Intent intent = new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.MAIN_ACTIVITY);
                intent.putExtra(AppConstant.SELLER_NAME, item.getSellerName());
                intent.putExtra(AppConstant.MESSAGE_ID, item.getId());
                startActivity(intent);
            }
        });

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
