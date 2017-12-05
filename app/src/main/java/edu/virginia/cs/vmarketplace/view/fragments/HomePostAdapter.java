package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.nosql.UserProfileDO;

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
        new UserProfileDOLoadAsyncTask().execute("");



    }

    class UserProfileDOLoadAsyncTask extends AsyncTask<String, Void, UserProfileDO> {
        private View rootView;

        public UserProfileDOLoadAsyncTask(View view) {
            this.rootView = view;
        }

        @Override
        protected UserProfileDO doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(UserProfileDO userProfileDO) {
            ImageView userAvatar = rootView.findViewById(R.id.home_post_avatar);
            if(userProfileDO.getAvatar() == null) {
                userAvatar.setImageResource(R.drawable.user_24p);
            } else {
                Picasso.with(getContext()).load(userProfileDO.getAvatar()).
                        placeholder(R.drawable.product_placeholder_96dp).into(userAvatar);
            }

            TextView userName = rootView.findViewById(R.id.home_post_user_name);
            userName.setText(userProfileDO.getUserName());

            TextView userDep = rootView.findViewById(R.id.home_post_user_dep);
            userDep.setText(userProfileDO.);
        }
    }
}
