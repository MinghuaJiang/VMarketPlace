package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class ProfilePublishItemAdapter extends ArrayAdapter<PublishItem> {
    private TransferUtility utility;
    private DynamoDBMapper mapper;

    public ProfilePublishItemAdapter(@NonNull Context context, @NonNull List<PublishItem> objects) {
        super(context, 0, objects);
        utility = AWSClientFactory.getInstance().getTransferUtility(context);
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.profile_publish_item, parent
                    , false);
        }

        final PublishItem currentItem = getItem(position);

        final ImageView imageView = listView.findViewById(R.id.image);
        if (currentItem.getImage() == null) {
            imageView.setImageResource(R.drawable.product_placeholder_96dp);
        } else {
            imageView.setImageResource(R.drawable.product_placeholder_96dp);
            final File file = new File(currentItem.getItemsDO().getOriginalFiles().get(0));
            utility.download(currentItem.getImage(),
                    file,
                    new TransferListener() {
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if (state == TransferState.COMPLETED) {
                                Picasso.with(getContext()).load(file).
                                        placeholder(R.drawable.product_placeholder_96dp).into(imageView);
                            }
                        }

                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        }

                        @Override
                        public void onError(int id, Exception ex) {
                            imageView.setImageResource(R.drawable.product_placeholder_96dp);
                        }
                    });
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContextManager.getContextManager().getAppContext().setItemsDO(currentItem.getItemsDO());
                Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.PUBLISH_BY_ME);
                getContext().startActivity(intent);
            }
        });

        TextView titleView = listView.findViewById(R.id.title);
        titleView.setText(currentItem.getTitle());

        TextView priceView = listView.findViewById(R.id.price);
        priceView.setText("$" + currentItem.getPrice());

        TextView countView = listView.findViewById(R.id.count);
        countView.setText("reply" + currentItem.getReplyCount() +
                "  " + "view" + currentItem.getViewCount());

        TextView typeView = listView.findViewById(R.id.product_type);
        typeView.setText(currentItem.getProductType());

        Button edit = listView.findViewById(R.id.modify);
        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppContextManager.getContextManager().getAppContext().setPublish(false);
                        AppContextManager.getContextManager().getAppContext().setItemsDO(currentItem.getItemsDO());
                        Intent intent = new Intent(getContext(), PublishFormActivity.class);
                        intent.putExtra(AppConstant.JUMP_FROM, AppConstant.PUBLISH_BY_ME);
                        getContext().startActivity(intent);
                    }
                }
        );

        Button delete = listView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductItemsDO itemsDO = currentItem.getItemsDO();
                List<String> files = itemsDO.getOriginalFiles();
                for(String each : files){
                    new File(each).delete();
                }
                new ProductItemDeleteTask(mapper, currentItem).execute(itemsDO);
            }
        });

        return listView;
    }

    class ProductItemDeleteTask extends AsyncTask<ProductItemsDO,Void, Void> {
        private PublishItem item;
        private DynamoDBMapper mapper;

        public ProductItemDeleteTask(DynamoDBMapper mapper, PublishItem item){
            this.mapper = mapper;
            this.item = item;
        }
        @Override
        protected Void doInBackground(ProductItemsDO... productItemsDOS) {
            mapper.delete(productItemsDOS[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ProfilePublishItemAdapter.this.remove(item);
            Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
