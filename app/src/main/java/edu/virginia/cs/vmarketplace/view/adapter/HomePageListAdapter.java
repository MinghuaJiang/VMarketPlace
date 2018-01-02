package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.LocationUtil;
import edu.virginia.cs.vmarketplace.util.TimeUtil;
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;
import edu.virginia.cs.vmarketplace.view.adapter.decoration.ItemOffsetDecoration;
import edu.virginia.cs.vmarketplace.view.adapter.model.ImageGalleryItem;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.FootViewHolder;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomePageListAdapter extends RefreshableRecycleAdapter<ProductItemsDO, RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_TAB = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_FOOTER = 3;
    private HeaderViewHolder headerViewHolder;
    private TabViewHolder tabViewHolder;
    private FootViewHolder footViewHolder;
    private HomeFragment fragment;

    public HomePageListAdapter(Context context, List<ProductItemsDO> items, HomeFragment fragment) {
        super(context, items);
        this.fragment = fragment;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }else if(isPositionTab(position)){
            return TYPE_TAB;
        }else if(isPositionFooter(position)){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }



    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionTab(int position) {
        return position == 1;
    }

    private boolean isPositionFooter(int position){
        return position == getItemCount() - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = getInflater().inflate(R.layout.home_header, parent, false);
            headerViewHolder = new HeaderViewHolder(view);
            return headerViewHolder;
        }else if(viewType == TYPE_TAB){
            View view = getInflater().inflate(R.layout.home_tab, parent, false);
            tabViewHolder = new TabViewHolder(view, fragment);
            return tabViewHolder;
        } else if (viewType == TYPE_ITEM) {
            View view = getInflater().inflate(R.layout.home_tab_list_item, parent, false);
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
    public int getItemCount() {
        return super.getItemCount() + 3;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder holder = (ItemViewHolder)viewHolder;
            ProductItemsDO productItemsDO = getItems().get(position - 2);
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

            // add thumb up
            TextView replyCount = holder.reply;

            if (productItemsDO.getReplyCount() == 0) {
                replyCount.setVisibility(View.GONE);
            } else {
                replyCount.setText("reply" + productItemsDO.getReplyCount().intValue());
                replyCount.setVisibility(View.VISIBLE);
            }

            TextView thumbUpCount = holder.thumbup;
            if (productItemsDO.getThumbUpCount() == 0) {
                thumbUpCount.setVisibility(View.GONE);
            } else {
                thumbUpCount.setText("like" + productItemsDO.getThumbUpCount().intValue());
                thumbUpCount.setVisibility(View.VISIBLE);

            }

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
                            intent.putExtra(AppConstant.JUMP_FROM, AppConstant.HOME_PAGE);
                            AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                            getContext().startActivity(intent);
                        }
                    }
            );

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                    intent.putExtra(AppConstant.JUMP_FROM, AppConstant.HOME_PAGE);
                    AppContextManager.getContextManager().getAppContext().setItemsDO(productItemsDO);
                    getContext().startActivity(intent);
                }
            });
        }else if(viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder holder = (HeaderViewHolder)viewHolder;
            holder.mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            holder.mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            holder.mSlider.setCustomAnimation(new DescriptionAnimation());
            holder.mSlider.setDuration(4000);
            holder.mSlider.startAutoCycle();
            holder.secondHand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.sublease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.rides.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else if(viewHolder instanceof FootViewHolder){
            FootViewHolder holder = (FootViewHolder)viewHolder;
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        private SliderLayout mSlider;
        private LinearLayout secondHand;
        private LinearLayout rides;
        private LinearLayout sublease;
        private LinearLayout activity;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            mSlider = itemView.findViewById(R.id.slider);
            int[] sliderIds = new int[]{R.drawable.uva_rotunda_spring, R.drawable.uva_rotunda_summer, R.drawable.uva_rotunda, R.drawable.uva_rotunda_winter};
            String[] sliderNames = new String[]{"Spring", "Summer", "Fall", "Winter"};
            for(int i = 0;i < sliderIds.length;i++){
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView
                        .description(sliderNames[i])
                        .image(sliderIds[i])
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mSlider.addSlider(textSliderView);
            }
            secondHand = itemView.findViewById(R.id.second_hand);
            rides = itemView.findViewById(R.id.rides);
            sublease = itemView.findViewById(R.id.sublease);
            activity = itemView.findViewById(R.id.activity);
        }

        public void stopAutoCycle(){
            mSlider.stopAutoCycle();
        }

        public void startAutoCycle(){
            mSlider.startAutoCycle();
        }
    }

    public class TabViewHolder extends RecyclerView.ViewHolder{
        public TabLayout tabLayout;
        public TabViewHolder(View itemView, HomeFragment fragment) {
            super(itemView);
            tabLayout = itemView.findViewById(R.id.tab);
            TabLayout.Tab tabNew = tabLayout.newTab();
            tabNew.setText("Latest");
            TabLayout.Tab tabNearBy = tabLayout.newTab();
            tabNearBy.setText("NearBy");
            tabLayout.addTab(tabNew);
            tabLayout.addTab(tabNearBy);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    fragment.setOnTabListener(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

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

        public ItemViewHolder(View itemView) {
            super(itemView);
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
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            gallery.addItemDecoration(itemDecoration);
        }
    }

    public HeaderViewHolder getHeaderViewHolder() {
        return headerViewHolder;
    }

    public TabViewHolder getTabViewHolder() {
        return tabViewHolder;
    }

    public FootViewHolder getFootViewHolder(){
        return footViewHolder;
    }
}
