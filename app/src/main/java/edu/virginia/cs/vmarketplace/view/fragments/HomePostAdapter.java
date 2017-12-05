package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomePostAdapter extends ArrayAdapter<ProductItemsDO> {
    public HomePostAdapter(@NonNull Context context, @NonNull ProductItemsDO[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.home_tab, parent
                    ,false);
        }
        ProductItemsDO productItemsDO = getItem(position);
        UserDO userDO = getUser(productItemsDO.getUserId());

        ImageView userAvatar = listView.findViewById(R.id.home_post_avatar);
        if(userDO.getAvatar() == null) {
            userAvatar.setImageResource(R.drawable.user_24p);
        } else {
            Picasso.with(getContext()).load(userDO.getAvatar()).
                    placeHolder(R.drawable.product_placeholder_96dp).into(userAvatar);
        }

    }
}
