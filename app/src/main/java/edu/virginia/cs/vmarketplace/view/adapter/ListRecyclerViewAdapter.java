package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.CategoryUtil;
import edu.virginia.cs.vmarketplace.util.ReflectionUtil;
import edu.virginia.cs.vmarketplace.view.CategoryActivity;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.ItemViewHolder;

/**
 * Created by mijian on 3/3/2018.
 */

public class ListRecyclerViewAdapter extends RefreshableRecycleAdapter<ProductItemsDO, RecyclerView.ViewHolder> {
    private static final int TYPE_TAB = 0;
    private static final int TYPE_ITEM = 1;
    private String category;
    private List<String> tabItem;
    private int backgroundResourceId;
    private TabViewHolder tabViewHolder;
    private ItemViewHolder itemViewHolder;
    private CategoryActivity activity;

    public ListRecyclerViewAdapter(Context context, List<ProductItemsDO> items, String category, CategoryActivity activity){
        super(context, items);
        this.category = category;
        this.tabItem = CategoryUtil.getSubCategory(category);
        this.backgroundResourceId = (int)ReflectionUtil.getConstant(R.color.class, category.replaceAll(" ",""));
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionTab(position)) {
            return TYPE_TAB;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionTab(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TAB) {
            View view = getInflater().inflate(R.layout.category_tab, parent, false);
            tabViewHolder = new TabViewHolder(view, tabItem, backgroundResourceId);
            return tabViewHolder;
        }else if(viewType == TYPE_ITEM) {
            View view = getInflater().inflate(R.layout.home_tab_list_item, parent, false);
            itemViewHolder = new ItemViewHolder(view, getContext());
            return itemViewHolder;
        } else {
            throw new RuntimeException("No matching viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ItemViewHolder){
            ItemViewHolder holder = (ItemViewHolder)viewHolder;
            ProductItemsDO productItemsDO = getItems().get(position - 1);
            holder.bindProductsItemDO(productItemsDO);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    class TabViewHolder extends RecyclerView.ViewHolder {
        public TabLayout layout;
        private List<String> tabItem;
        private int backgroundResourceId;

        public TabViewHolder(View itemView, List<String> tabItem, int backgroundResourceId) {
            super(itemView);
            this.tabItem = tabItem;
            this.backgroundResourceId = backgroundResourceId;
            layout = itemView.findViewById(R.id.tab);
            layout.setBackgroundColor(ContextCompat.getColor(getContext(), backgroundResourceId));
            for(String tabName : this.tabItem){
                TabLayout.Tab tab = layout.newTab();
                tab.setText(tabName);
                layout.addTab(tab);
            }
            layout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.tan_background), ContextCompat.getColor(getContext(), R.color.barBackground));
            layout.post(new Runnable() {
                @Override
                public void run() {
                    int tabLayoutWidth = layout.getWidth();
                    DisplayMetrics metrics = new DisplayMetrics();
                    ListRecyclerViewAdapter.this.activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int deviceWidth = metrics.widthPixels;
                    if(tabLayoutWidth < deviceWidth){
                        layout.setMinimumWidth(deviceWidth);
                        layout.setTabMode(TabLayout.MODE_FIXED);
                        layout.setTabGravity(TabLayout.GRAVITY_FILL);
                    }else {
                        layout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                }
            });
            layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
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
}
