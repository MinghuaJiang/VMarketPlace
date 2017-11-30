package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.model.SoldItem;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class ProfileSoldItemAdapter extends ArrayAdapter<SoldItem> {
    public ProfileSoldItemAdapter(@NonNull Context context, @NonNull List<SoldItem> objects) {
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

        TextView typeView = listView.findViewById(R.id.product_type);
        typeView.setText(currentItem.getProductType());

        Button reviewButton = listView.findViewById(R.id.review);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SoldReviewActivity.class);
                getContext().startActivity(intent);
            }
        });

        Button messageButton = listView.findViewById(R.id.contact);

        messageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.SOLD_ACTIVITY);
                getContext().startActivity(intent);
            }
        });

        return listView;
    }
}
