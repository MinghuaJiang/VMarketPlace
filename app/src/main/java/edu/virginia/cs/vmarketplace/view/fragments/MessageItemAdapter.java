package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.MessageItem;


/**
 * Created by cutehuazai on 11/24/17.
 */

public class MessageItemAdapter extends ArrayAdapter<MessageItem> {
    public MessageItemAdapter(@NonNull Context context, @NonNull List<MessageItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.message_list_item, parent
                    ,false);
        }

        MessageItem currentItem = getItem(position);

        CircleImageView imageView = listView.findViewById(R.id.image);
        Picasso.with(getContext()).load(currentItem.getSellerPic())
                .placeholder(R.drawable.place_holder_96p).fit().into(imageView);

        TextView typeView = listView.findViewById(R.id.name);
        typeView.setText(currentItem.getSellerName());

        TextView messageView = listView.findViewById(R.id.message);
        messageView.setText(currentItem.getLatestMessage());

        TextView timeView = listView.findViewById(R.id.time);
        timeView.setText(currentItem.getLatestUpdateTime());

        ImageView picView = listView.findViewById(R.id.thumbpic);
        Picasso.with(getContext()).load(currentItem.getThumbPic())
                .placeholder(R.drawable.place_holder_96p).fit().into(picView);

        return listView;
    }
}
