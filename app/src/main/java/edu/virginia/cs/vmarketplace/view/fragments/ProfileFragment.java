package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.AppUser;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.view.BoughtActivity;
import edu.virginia.cs.vmarketplace.view.FavoriteActivity;
import edu.virginia.cs.vmarketplace.view.ProfilePublishActivity;
import edu.virginia.cs.vmarketplace.view.ProfileSoldActivity;
import edu.virginia.cs.vmarketplace.view.login.AWSLoginActivity;

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
        final View rootView = inflater.inflate(R.layout.profile, container, false);

        AppUser user = AppContextManager.getContextManager()
                .getAppContext().getUser();

        TextView textView = rootView.findViewById(R.id.user_id);
        textView.setText(user.getUsername());

        TextView ratingView = rootView.findViewById(R.id.user_rating);
        if(user.getUserRating() == null) {
            ratingView.setText("Rating  "+"Newcomer");
        }else{
            ratingView.setText("Rating  "+user.getUserRating());
        }
        CircleImageView imageView = rootView.findViewById(R.id.user_pic);
        if(user.getUserPic() != null) {
            Picasso.with(getActivity()).load(user.getUserPic()).fit().placeholder(R.drawable.place_holder_96p).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.place_holder_96p);
        }


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
        Button button = rootView.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContextManager.getContextManager().getAppContext().signOut();
                Intent intent = new Intent(getActivity(), AWSLoginActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void handlePublish(){
        Intent intent = new Intent(getActivity(), ProfilePublishActivity.class);
        startActivity(intent);
    }

    private void handleSold(){
        Intent intent = new Intent(getActivity(), ProfileSoldActivity.class);
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
