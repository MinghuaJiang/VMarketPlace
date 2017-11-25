package edu.virginia.cs.vmarketplace.view;

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

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.model.PublishItem;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class ProfilePublishItemAdapter extends ArrayAdapter<PublishItem> {
    public ProfilePublishItemAdapter(@NonNull Context context, @NonNull List<PublishItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.profile_publish_item, parent
                    ,false);
        }

        PublishItem currentItem = getItem(position);

        ImageView imageView = listView.findViewById(R.id.image);
        if(currentItem.getImage() == null){
            imageView.setImageResource(R.drawable.ride_96dp);
        }else{
            Picasso.with(getContext()).load(currentItem.getImage()).
                    placeholder(R.drawable.product_placeholder_96dp).into(imageView);
        }

        TextView titleView = listView.findViewById(R.id.title);
        titleView.setText(currentItem.getTitle());

        TextView priceView = listView.findViewById(R.id.price);
        priceView.setText("$" + currentItem.getPrice());

        TextView countView = listView.findViewById(R.id.count);
        countView.setText("reply" + currentItem.getReplyCount() +
                "  "+"view" + currentItem.getViewCount());

        TextView typeView = listView.findViewById(R.id.product_type);
        typeView.setText(currentItem.getProductType());

        return listView;
    }
}
