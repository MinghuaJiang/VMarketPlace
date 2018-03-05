package edu.virginia.cs.vmarketplace.view.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.dao.ProductItemDao;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.LocationUtil;
import edu.virginia.cs.vmarketplace.util.TimeUtil;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;
import edu.virginia.cs.vmarketplace.view.adapter.ImageGalleryAdapter;
import edu.virginia.cs.vmarketplace.view.adapter.decoration.ItemOffsetDecoration;
import edu.virginia.cs.vmarketplace.view.adapter.model.ImageGalleryItem;

/**
 * Created by mijian on 3/3/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView userAvatar;
    public TextView userName;
    public TextView time;
    public TextView price;
    public TextView title;
    public TextView description;
    public TextView location;
    public TextView reply;
    public TextView thumbup;
    public RecyclerView gallery;
    public LinearLayout layout;
    private Context context;

    public ItemViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        userAvatar = itemView.findViewById(R.id.home_post_avatar);
        userName = itemView.findViewById(R.id.home_post_user_name);
        price = itemView.findViewById(R.id.product_price);
        title = itemView.findViewById(R.id.home_post_title);
        time = itemView.findViewById(R.id.home_post_time);
        description = itemView.findViewById(R.id.home_post_description);
        location = itemView.findViewById(R.id.home_post_locale);
        thumbup = itemView.findViewById(R.id.home_post_thumbup);
        reply = itemView.findViewById(R.id.home_post_reply);
        gallery = itemView.findViewById(R.id.home_post_image_gallery);
        layout = itemView.findViewById(R.id.container);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this.context, R.dimen.item_offset);
        gallery.addItemDecoration(itemDecoration);
    }

    public Context getContext(){
        return context;
    }

    public void bindProductsItemDO(ProductItemsDO productItemsDO){
        if (productItemsDO.getCreatedByAvatar() == null) {
            userAvatar.setImageResource(R.drawable.placeholder);
        } else if (productItemsDO.getCreatedByAvatar().startsWith(S3Service.S3_PREFIX)) {
            S3Service.getInstance(getContext()).download(productItemsDO.getCreatedByAvatar(), (x) -> {
                Picasso.with(getContext()).load(x.get(0)).
                        placeholder(R.drawable.placeholder).into(userAvatar);
            });
        } else {
            Picasso.with(getContext()).load(productItemsDO.getCreatedByAvatar()).
                    placeholder(R.drawable.placeholder).into(userAvatar);
        }

        userName.setText(productItemsDO.getCreatedByName());
        price.setText("$" + productItemsDO.getPrice());
        title.setText(productItemsDO.getTitle());
        time.setText(TimeUtil.getRelativeTimeFromNow(productItemsDO.getLastModificationTime()));
        if (productItemsDO.getDescription() == null) {
            description.setVisibility(View.GONE);
        } else {
            description.setVisibility(View.VISIBLE);
            if (productItemsDO.getDescription().length() >= 95) {
                description.setText(
                        productItemsDO.getDescription().substring(0, 95) + "...");
            } else {
                description.setText(productItemsDO.getDescription());
            }
        }
        location.setText(LocationUtil.getAddressAndZipCode(productItemsDO.getLocation()));

        // add thumb up
        TextView replyCount = reply;

        if (productItemsDO.getReplyCount() == 0) {
            replyCount.setVisibility(View.GONE);
        } else {
            replyCount.setText("reply" + productItemsDO.getReplyCount().intValue());
            replyCount.setVisibility(View.VISIBLE);
        }

        TextView thumbUpCount = thumbup;
        if (productItemsDO.getThumbUpCount() == 0) {
            thumbUpCount.setVisibility(View.GONE);
        } else {
            thumbUpCount.setText("like" + productItemsDO.getThumbUpCount().intValue());
            thumbUpCount.setVisibility(View.VISIBLE);

        }

        gallery.setFocusable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        gallery.setLayoutManager(linearLayoutManager);

        List<String> originalFiles = productItemsDO.getOriginalFiles();
        List<String> s3Urls = productItemsDO.getPics();

        List<ImageGalleryItem> list = new ArrayList<>();
        for (int i = 0; i < originalFiles.size(); i++) {
            ImageGalleryItem item = new ImageGalleryItem(originalFiles.get(i),
                    s3Urls.get(i));
            list.add(item);
        }

        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(getContext(), list);
        gallery.setAdapter(imageGalleryAdapter);

        imageGalleryAdapter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                        intent.putExtra(AppConstant.JUMP_FROM, AppConstant.HOME_PAGE);
                        AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                        getContext().startActivity(intent);
                    }
                }
        );

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                intent.putExtra(AppConstant.JUMP_FROM, AppConstant.PRODUCT_LIST);
                AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                getContext().startActivity(intent);
            }
        });
    }
}
