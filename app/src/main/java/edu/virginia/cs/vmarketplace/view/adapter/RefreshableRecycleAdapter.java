package edu.virginia.cs.vmarketplace.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by cutehuazai on 12/15/17.
 */

public abstract class RefreshableRecycleAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    private List<T> items;
    private Context context;
    private LayoutInflater inflater;

    public RefreshableRecycleAdapter(Context context, List<T> items){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setData(List<T> data, int start){
        if(null != data && !data.isEmpty()){
            synchronized (items){
                items.clear();
                items.addAll(data);
            }
            notifyItemRangeChanged(start, items.size());
        }
    }
}
