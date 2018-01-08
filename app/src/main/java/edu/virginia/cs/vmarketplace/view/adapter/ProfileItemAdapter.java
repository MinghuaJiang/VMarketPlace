package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfileItemAdapter extends ArrayAdapter<ProfileItem> {
    public ProfileItemAdapter(@NonNull Context context, @NonNull List<ProfileItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.profile_list_item, parent
                    ,false);
        }

        ProfileItem currentItem = getItem(position);

        TextView typeView = listView.findViewById(R.id.profiletype);
        typeView.setText(currentItem.getProfileType());

        ImageView imageView = listView.findViewById(R.id.profileimage);
        if(currentItem.getmProfileTypeImageResourceId() != -1) {
            imageView.setImageResource(currentItem.getmProfileTypeImageResourceId());
        }else{
            imageView.setVisibility(View.GONE);
        }

        TextView countView = listView.findViewById(R.id.profilecount);
        if(currentItem.getCount() != -1) {
            countView.setText(String.valueOf(currentItem.getCount()));
        }else{
            countView.setVisibility(View.GONE);
        }

        TextView profileClick = listView.findViewById(R.id.profile_click);
        if(currentItem.isHaveSubMenu() == false){
            profileClick.setVisibility(View.GONE);
        }else{
            profileClick.setVisibility(View.VISIBLE);
        }


        return listView;
    }
}
