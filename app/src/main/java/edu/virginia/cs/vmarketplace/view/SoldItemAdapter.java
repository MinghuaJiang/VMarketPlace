package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.model.SoldItem;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class SoldItemAdapter extends ArrayAdapter<SoldItem> {
    public SoldItemAdapter(@NonNull Context context, @NonNull List<SoldItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.profile_sold_item, parent
                    ,false);
        }

        SoldItem currentItem = getItem(position);

        ImageView imageView = listView.findViewById(R.id.image);
        imageView.setImageResource(currentItem.getImageResourceId());

        TextView titleView = listView.findViewById(R.id.title);
        titleView.setText(currentItem.getTitle());

        TextView priceView = listView.findViewById(R.id.price);
        priceView.setText("$" + currentItem.getPrice());

        return listView;
    }
}
