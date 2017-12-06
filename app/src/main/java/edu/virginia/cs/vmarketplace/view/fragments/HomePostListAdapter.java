package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomePostListAdapter extends ArrayAdapter<ProductItemsDO> {
    private List<File> images;
    private TransferUtility transferUtility;
    private int imageCounter;

    public HomePostListAdapter(Context context, List<ProductItemsDO> objects) {
        super(context, 0, objects);
        this.images = new ArrayList<>();
        this.transferUtility = AWSClientFactory.getInstance().getTransferUtility(context);
        this.imageCounter = 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.home_tab, parent
                    ,false);
        }
        ProductItemsDO productItemsDO = getItem(position);
        if (productItemsDO.getCreatedBy() != null) {
            new UserProfileDOLoadAsyncTask(listView).execute(productItemsDO.getCreatedBy());
        }

        // download images from S3 to list item
        if (productItemsDO.getPics() != null) {
            final int picsTotalCount = productItemsDO.getPics().size();
            GridView gridView = listView.findViewById(R.id.home_post_image_gallery);
            for(int i=0; i<picsTotalCount; i++) {
                File img = new File(getContext().getExternalFilesDir(null)
                        + File.separator + i + ".jpg");
                transferUtility.download(productItemsDO.getPics().get(i), img, new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        if (imageCounter == picsTotalCount) {
                            // initialize GridView
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                    }

                    @Override
                    public void onError(int id, Exception ex) {

                    }
                });
                imageCounter++;
                images.add(img);
            }
        }


        // add post title
        TextView postTitle = listView.findViewById(R.id.home_post_title);
        if (productItemsDO.getTitle() != null) {
            postTitle.setText(productItemsDO.getTitle());
        }

        // add post location
        TextView postLocale = listView.findViewById(R.id.home_post_locale);
        if (productItemsDO.getLocation() != null) {
            postLocale.setText(productItemsDO.getLocation());
        }

        // add thumb up
        TextView postThumbUp = listView.findViewById(R.id.home_post_thumbup);
        postThumbUp.setText("" + 5);
        System.out.println("ListItem created");
        System.out.println("number of Images in images" + images.size() + " : " + imageCounter);
        return listView;
    }

    class UserProfileDOLoadAsyncTask extends AsyncTask<String, Void, UserProfileDO> {
        private View rootView;
        private DynamoDBMapper mapper;

        public UserProfileDOLoadAsyncTask(View view) {
            this.rootView = view;
            this.mapper = AWSClientFactory.getInstance().getDBMapper();
        }

        @Override
        protected UserProfileDO doInBackground(String... strings) {
            return mapper.load(UserProfileDO.class, strings[0]);
        }

        @Override
        protected void onPostExecute(UserProfileDO userProfileDO) {
//            System.out.println("user : " + userProfileDO.toString());
            CircleImageView userAvatar = rootView.findViewById(R.id.home_post_avatar);
            if(userProfileDO.getAvatar() == null) {
                userAvatar.setImageResource(R.drawable.user_24p);
            } else {
                Picasso.with(getContext()).load(userProfileDO.getAvatar()).
                        placeholder(R.drawable.product_placeholder_96dp).into(userAvatar);
            }

            TextView userName = rootView.findViewById(R.id.home_post_user_name);
            userName.setText(userProfileDO.getUserName());

            TextView userDep = rootView.findViewById(R.id.home_post_user_dep);
            userDep.setText(userProfileDO.getDepartment());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
