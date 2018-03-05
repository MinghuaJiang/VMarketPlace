package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import edu.virginia.cs.vmarketplace.view.CategoryActivity;
import edu.virginia.cs.vmarketplace.view.MainActivity;
import edu.virginia.cs.vmarketplace.view.PublishDetailActivity;
import edu.virginia.cs.vmarketplace.view.adapter.decoration.ItemOffsetDecoration;
import edu.virginia.cs.vmarketplace.view.adapter.model.ImageGalleryItem;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.FootViewHolder;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.ItemViewHolder;
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
            ItemViewHolder itemViewHolder = new ItemViewHolder(view, getContext());
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
            holder.bindProductsItemDO(productItemsDO);
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
                    Intent intent = new Intent(getContext(), CategoryActivity.class);
                    intent.putExtra(AppConstant.CATEGORY, "Second Hand");
                    getContext().startActivity(intent);
                }
            });
            holder.activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CategoryActivity.class);
                    intent.putExtra(AppConstant.CATEGORY, "Activity");
                    getContext().startActivity(intent);
                }
            });
            holder.sublease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CategoryActivity.class);
                    intent.putExtra(AppConstant.CATEGORY, "Sublease");
                    getContext().startActivity(intent);
                }
            });
            holder.rides.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), CategoryActivity.class);
                    intent.putExtra(AppConstant.CATEGORY, "Ride");
                    getContext().startActivity(intent);
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
