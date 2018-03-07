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
import edu.virginia.cs.vmarketplace.view.AppConstant;
import edu.virginia.cs.vmarketplace.view.CategoryActivity;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.FootViewHolder;
import edu.virginia.cs.vmarketplace.view.adapter.viewholder.ItemViewHolder;

/**
 * Created by mijian on 3/3/2018.
 */

public class ListRecyclerViewAdapter extends RefreshableRecycleAdapter<ProductItemsDO, RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ItemViewHolder itemViewHolder;
    private FootViewHolder footViewHolder;

    public ListRecyclerViewAdapter(Context context, List<ProductItemsDO> items){
        super(context, items);
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionFooter(position)){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionFooter(int position){
        return position == getItemCount() - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = getInflater().inflate(R.layout.home_tab_list_item, parent, false);
            itemViewHolder = new ItemViewHolder(view, getContext(), AppConstant.CATEGORY_PAGE);
            return itemViewHolder;
        }else if(viewType == TYPE_FOOTER) {
            View view = getInflater().inflate(R.layout.home_tab_footer, parent, false);
            footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        }else {
            throw new RuntimeException("No matching viewType");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ItemViewHolder){
            ItemViewHolder holder = (ItemViewHolder)viewHolder;
            ProductItemsDO productItemsDO = getItems().get(position);
            holder.bindProductsItemDO(productItemsDO);
        }else if(viewHolder instanceof FootViewHolder){
            FootViewHolder holder = (FootViewHolder)viewHolder;
            holder.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }


    public FootViewHolder getFootViewHolder() {
        return footViewHolder;
    }
}
