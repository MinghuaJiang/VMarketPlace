package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.model.User;
import edu.virginia.cs.vmarketplace.view.BoughtActivity;
import edu.virginia.cs.vmarketplace.view.FavoriteActivity;
import edu.virginia.cs.vmarketplace.view.PublishActivity;
import edu.virginia.cs.vmarketplace.view.SoldActivity;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfileFragment extends AbstractFragment{
    public ProfileFragment(){
        super("profile", R.drawable.user_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);
        User user = new User("helloword", "");
        TextView textView = rootView.findViewById(R.id.user_id);
        textView.setText(user.getUsername());
        CircleImageView imageView = rootView.findViewById(R.id.user_pic);
        Picasso.with(getActivity()).load(user.getUserPic()).fit().into(imageView);

        List<ProfileItem> list = new ArrayList<ProfileItem>();
        list.add(new ProfileItem(R.drawable.publish_24p, ProfileItem.ProfileType.PUBLISH_BY_ME, 5));
        list.add(new ProfileItem(R.drawable.sold_24p, ProfileItem.ProfileType.SOLD_BY_ME, 3));
        list.add(new ProfileItem(R.drawable.bought_24p, ProfileItem.ProfileType.BOUGHT_BY_ME, 10));
        list.add(new ProfileItem(R.drawable.star_24p, ProfileItem.ProfileType.ADDED_TO_FAVIORITE, 5));
        ProfileItemAdapter adapter = new ProfileItemAdapter(getActivity(), list);
        ListView listView = rootView.findViewById(R.id.profile_container);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    handlePublish();
                }else if(position == 1){
                    handleSold();
                }else if(position == 2){
                    handleBought();
                }else{
                    handleFavorite();
                }
            }
        });

        ListView settingView =  rootView.findViewById(R.id.settings_container);
        list = new ArrayList<ProfileItem>();
        list.add(new ProfileItem(R.drawable.settings_24p, ProfileItem.ProfileType.SETTING, -1));
        adapter = new ProfileItemAdapter(getActivity(), list);
        settingView.setAdapter(adapter);

        return rootView;
    }

    private void handlePublish(){
        Intent intent = new Intent(getActivity(), PublishActivity.class);
        startActivity(intent);
    }

    private void handleSold(){
        Intent intent = new Intent(getActivity(), SoldActivity.class);
        startActivity(intent);
    }

    private void handleBought(){
        Intent intent = new Intent(getActivity(), BoughtActivity.class);
        startActivity(intent);
    }

    private void handleFavorite(){
        Intent intent = new Intent(getActivity(), FavoriteActivity.class);
        startActivity(intent);
    }
}
