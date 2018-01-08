package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.CommentsDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.CommentService;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.UserProfileService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;
import edu.virginia.cs.vmarketplace.view.PublishFormActivity;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class ProfilePublishItemAdapter extends ArrayAdapter<ProductItemsDO> {
    private AppContext appContext;
    public ProfilePublishItemAdapter(@NonNull Context context, @NonNull List<ProductItemsDO> objects) {
        super(context, 0, objects);
        appContext = AppContextManager.getContextManager().getAppContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.profile_publish_item, parent
                    , false);
        }
        final ProductItemsDO currentItem = getItem(position);

        final ImageView imageView = listView.findViewById(R.id.image);
        if (currentItem.getThumbPic() == null) {
            imageView.setImageResource(R.drawable.product_placeholder_96dp);
        } else {
            imageView.setImageResource(R.drawable.product_placeholder_96dp);
            final File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + currentItem.getOriginalFiles().get(0));
            if(!file.exists()) {
                S3Service.getInstance(getContext()).download(currentItem.getThumbPic(),file.getName(),
                        (x) -> {
                            Picasso.with(getContext()).load(x.get(0)).
                                    placeholder(R.drawable.product_placeholder_96dp).into(imageView);
                        });
            }else{
                Picasso.with(getContext()).load(file).
                        placeholder(R.drawable.product_placeholder_96dp).into(imageView);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContextManager.getContextManager().getAppContext().setItemsDO(currentItem);
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
        countView.setText("reply" + currentItem.getReplyCount().intValue() +
                "  " + "view" + currentItem.getViewCount().intValue());

        TextView typeView = listView.findViewById(R.id.product_type);
        typeView.setText(currentItem.getCategory() + " - " + currentItem.getSubcategory());

        Button edit = listView.findViewById(R.id.modify);
        edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppContextManager.getContextManager().getAppContext().setPublish(false);
                        AppContextManager.getContextManager().getAppContext().setItemsDO(currentItem);
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
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Item")
                        .setMessage("Do you really want to delete it?")
                        .setIcon(R.drawable.warning)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ProductItemsDO itemsDO = currentItem;
                                List<String> files = itemsDO.getOriginalFiles();
                                for(String each : files){
                                    new File(each).delete();
                                }
                                appContext.getUserDO().getPublishItems().remove(itemsDO.getItemId());
                                new CommonAyncTask<UserProfileDO, Void, Void>(
                                        UserProfileService.getInstance()::insertOrUpdate, appContext.getUserDO()).run();
                                new CommonAyncTask<ProductItemsDO, Void, ProductItemsDO>((x)->{
                                    List<UserProfileDO> users = UserProfileService.getInstance().findUsersByIds(new ArrayList<>(x.getFavoriteUserIds()));
                                    for(UserProfileDO user: users){
                                        user.getFavoriteItems().remove(x.getItemId());
                                        //Add notification logic
                                    }
                                    UserProfileService.getInstance().batchSave(users);
                                    return ProductItemService.getInstance().delete(x);
                                },
                                        itemsDO).with(
                                        (x)-> {
                                            ProfilePublishItemAdapter.this.remove(x);
                                            Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                ).run();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        return listView;
    }
}
