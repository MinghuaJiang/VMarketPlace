package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
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
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.service.login.AppUser;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.view.ProfileBoughtActivity;
import edu.virginia.cs.vmarketplace.view.ProfileFavoriteActivity;
import edu.virginia.cs.vmarketplace.view.ProfilePublishActivity;
import edu.virginia.cs.vmarketplace.view.ProfileSoldActivity;
import edu.virginia.cs.vmarketplace.service.loader.ProfileItemLoader;
import edu.virginia.cs.vmarketplace.view.adapter.ProfileItemAdapter;
import edu.virginia.cs.vmarketplace.view.login.AWSLoginActivity;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ProfileFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<List<ProfileItem>>{

    private ProfileItemAdapter adapter;

    public ProfileFragment(){
        super("profile", R.drawable.user_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profile, container, false);

        getActivity().getWindow().setStatusBarColor(
                getTabBackground());

        AppUser user = AppContextManager.getContextManager()
                .getAppContext().getUser();

        TextView textView = rootView.findViewById(R.id.user_id);
        textView.setText(user.getUserName());

        TextView ratingView = rootView.findViewById(R.id.user_rating);
        if(user.getUserRating() == null) {
            ratingView.setText("Rating  "+"Newcomer");
        }else{
            ratingView.setText("Rating  "+user.getUserRating());
        }
        CircleImageView imageView = rootView.findViewById(R.id.user_pic);
        if(user.getUserPic() == null && user.getUserPicUri() == null) {
            imageView.setImageResource(R.drawable.placeholder);
        }else if(user.getUserPicUri() != null){
            Picasso.with(getActivity()).load(user.getUserPicUri()).fit().placeholder(R.drawable.placeholder).into(imageView);
        }else{
            Picasso.with(getActivity()).load(user.getUserPic()).fit().placeholder(R.drawable.placeholder).into(imageView);
        }


        List<ProfileItem> list = new ArrayList<ProfileItem>();
        //add loader
        adapter = new ProfileItemAdapter(getActivity(), list);
        ListView listView = rootView.findViewById(R.id.profile_container);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0 ,null, this).forceLoad();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    handlePublish();
                }else if(position == 1){
                    handleSold();
                }else if(position == 2){
                    handleBought();
                }else if(position == 3){
                    handleFavorite();
                }else{
                    handleSettings();
                }
            }
        });

       /* Button button = rootView.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContextManager.getContextManager().getAppContext().signOut();
                Intent intent = new Intent(getActivity(), AWSLoginActivity.class);
                startActivity(intent);
            }
        });*/

        return rootView;
    }

    public int getTabBackground(){
        return ContextCompat.getColor(getContext(), R.color.tan_background);
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
        Intent intent = new Intent(getActivity(), ProfileBoughtActivity.class);
        startActivity(intent);
    }

    private void handleFavorite(){
        Intent intent = new Intent(getActivity(), ProfileFavoriteActivity.class);
        startActivity(intent);
    }

    private void handleSettings(){

    }

    @Override
    public Loader<List<ProfileItem>> onCreateLoader(int id, Bundle args) {
        return new ProfileItemLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<ProfileItem>> loader, List<ProfileItem> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ProfileItem>> loader) {
        adapter.clear();
    }
}
