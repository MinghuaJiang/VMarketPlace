package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.UserProfileService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.LocationUtil;
import edu.virginia.cs.vmarketplace.util.TimeUtil;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;
import edu.virginia.cs.vmarketplace.view.adapter.decoration.ItemOffsetDecoration;
import edu.virginia.cs.vmarketplace.view.adapter.model.ImageGalleryItem;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.FootViewHolder;

/**
 * Created by cutehuazai on 12/29/17.
 */

public class ProfileFavoriteItemAdapter extends RefreshableRecycleAdapter<ProductItemsDO, RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private FootViewHolder footViewHolder;
    private AppContext appContext;

    public ProfileFavoriteItemAdapter(Context context, List<ProductItemsDO> items) {
        super(context, items);
        appContext = AppContextManager.getContextManager().getAppContext();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionFooter(position)){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position){
        return position == getItemCount() - 1;
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = getInflater().inflate(R.layout.profile_favorite_item, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }else if(viewType == TYPE_FOOTER){
            View view = getInflater().inflate(R.layout.home_tab_footer, parent, false);
            footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        } else {
            throw new RuntimeException("No matching viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            ProductItemsDO productItemsDO = getItems().get(position);
            if (productItemsDO.getCreatedByAvatar() == null) {
                holder.userAvatar.setImageResource(R.drawable.placeholder);
            } else if (productItemsDO.getCreatedByAvatar().startsWith(S3Service.S3_PREFIX)) {
                S3Service.getInstance(getContext()).download(productItemsDO.getCreatedByAvatar(), (x) -> {
                    Picasso.with(getContext()).load(x.get(0)).
                            placeholder(R.drawable.placeholder).into(holder.userAvatar);
                });
            } else {
                Picasso.with(getContext()).load(productItemsDO.getCreatedByAvatar()).
                        placeholder(R.drawable.placeholder).into(holder.userAvatar);
            }

            holder.userName.setText(productItemsDO.getCreatedByName());
            holder.price.setText("$" + productItemsDO.getPrice());
            holder.title.setText(productItemsDO.getTitle());
            holder.time.setText(TimeUtil.getRelativeTimeFromNow(productItemsDO.getLastModificationTime()));
            TextView description = holder.description;
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
            holder.location.setText(LocationUtil.getAddressAndZipCode(productItemsDO.getLocation()));

            RecyclerView gallery = holder.gallery;
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
                            intent.putExtra(AppConstant.JUMP_FROM, AppConstant.MY_FAVORITE);
                            AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                            getContext().startActivity(intent);
                        }
                    }
            );

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                    intent.putExtra(AppConstant.JUMP_FROM, AppConstant.MY_FAVORITE);
                    AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                    getContext().startActivity(intent);
                }
            });

            if(appContext.getUserDO().getFavoriteItems().contains(productItemsDO.getItemId())) {
                holder.favorite.setBackgroundResource(R.drawable.border_yellow_star_black);
                holder.favorite.setText("Unfavorite");
            }else{
                holder.favorite.setBackgroundResource(R.drawable.border_star_black);
                holder.favorite.setText("Favorite");
            }

            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(appContext.getUserDO().getFavoriteItems().contains(productItemsDO.getItemId())) {
                        holder.favorite.setBackgroundResource(R.drawable.border_star_black);
                        holder.favorite.setText("Favorite");
                        appContext.getUserDO().getFavoriteItems().remove(productItemsDO.getItemId());
                        Toast.makeText(getContext(), "Unfavorite the item successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        holder.favorite.setBackgroundResource(R.drawable.border_yellow_star_black);
                        holder.favorite.setText("Unfavorite");
                        appContext.getUserDO().getFavoriteItems().add(productItemsDO.getItemId());
                        Toast.makeText(getContext(), "Favorite the item successfully!", Toast.LENGTH_SHORT).show();
                    }
                    new CommonAyncTask<UserProfileDO, Void, Void>(UserProfileService.getInstance()::insertOrUpdate, appContext.getUserDO()).run();
                }
            });

        }else{
            FootViewHolder holder = (FootViewHolder)viewHolder;
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public FootViewHolder getFootViewHolder(){
        return footViewHolder;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userAvatar;
        public TextView userName;
        public TextView time;
        public TextView price;
        public TextView title;
        public TextView description;
        public TextView location;
        public Button favorite;
        public RecyclerView gallery;
        public LinearLayout layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.home_post_avatar);
            userName = itemView.findViewById(R.id.home_post_user_name);
            price = itemView.findViewById(R.id.product_price);
            title = itemView.findViewById(R.id.home_post_title);
            time = itemView.findViewById(R.id.home_post_time);
            description = itemView.findViewById(R.id.home_post_description);
            location = itemView.findViewById(R.id.home_post_locale);
            favorite = itemView.findViewById(R.id.favorite);
            gallery = itemView.findViewById(R.id.home_post_image_gallery);
            layout = itemView.findViewById(R.id.container);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            gallery.addItemDecoration(itemDecoration);
        }
    }
}
