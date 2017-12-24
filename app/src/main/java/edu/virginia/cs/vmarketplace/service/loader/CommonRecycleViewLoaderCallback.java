package edu.virginia.cs.vmarketplace.service.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import edu.virginia.cs.vmarketplace.model.PageResult;
import edu.virginia.cs.vmarketplace.view.adapter.RefreshableRecycleAdapter;

/**
 * Created by cutehuazai on 12/14/17.
 */

public class CommonRecycleViewLoaderCallback <T, R> implements LoaderManager.LoaderCallbacks<PageResult<R>> {
    public RefreshableRecycleAdapter<R, RecyclerView.ViewHolder> adapter;
    private Function<T, PageResult<R>> function;
    private Supplier<PageResult<R>> supplier;
    private T param;
    private Context context;
    private CustomCallback<R> callback;

    public CommonRecycleViewLoaderCallback(Context context, RefreshableRecycleAdapter<R, RecyclerView.ViewHolder> adapter,
                                Supplier<PageResult<R>> supplier){
        this.context = context;
        this.adapter = adapter;
        this.supplier = supplier;
    }

    public CommonRecycleViewLoaderCallback(Context context, RefreshableRecycleAdapter<R, RecyclerView.ViewHolder> adapter, Function<T, PageResult<R>> function, T param){
        this.context = context;
        this.adapter = adapter;
        this.function = function;
        this.param = param;
    }

    public CommonRecycleViewLoaderCallback<T,R> with(CustomCallback<R> callback){
        this.callback = callback;
        return this;
    }

    @Override
    public Loader<PageResult<R>> onCreateLoader(int id, Bundle args) {
        return new CommonAsyncTaskLoader<T,PageResult<R>>(context, function, supplier, param);
    }

    @Override
    public void onLoadFinished(Loader<PageResult<R>> loader, PageResult<R> data) {
        if(callback != null){
            callback.runCallBack(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<PageResult<R>> loader) {
        adapter.setData(new ArrayList<>(), 0);
    }

    public interface CustomCallback<R>{
        public void runCallBack(PageResult<R> data);
    }
}
