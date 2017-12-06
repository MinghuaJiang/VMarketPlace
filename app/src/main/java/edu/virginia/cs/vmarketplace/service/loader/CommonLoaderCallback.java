package edu.virginia.cs.vmarketplace.service.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommonLoaderCallback<T, R> implements LoaderManager.LoaderCallbacks<List<R>> {
    public ArrayAdapter<R> adapter;
    private Function<T, List<R>> function;
    private Supplier<List<R>> supplier;
    private T param;
    private SwipeRefreshLayout refreshLayout;

    public CommonLoaderCallback(ArrayAdapter<R> adapter,
         Supplier<List<R>> supplier){
        this.adapter = adapter;
        this.supplier = supplier;
    }

    public CommonLoaderCallback(ArrayAdapter<R> adapter, Function<T, List<R>> function, T param){
        this.adapter = adapter;
        this.function = function;
        this.param = param;
    }

    public CommonLoaderCallback<T,R> with(SwipeRefreshLayout refreshLayout){
        this.refreshLayout = refreshLayout;
        return this;
    }

    @Override
    public Loader<List<R>> onCreateLoader(int id, Bundle args) {
        return new CommonAsyncTaskLoader<>(adapter.getContext(), function, supplier, param);
    }

    @Override
    public void onLoadFinished(Loader<List<R>> loader, List<R> data) {
        adapter.clear();
        adapter.addAll(data);
        if(refreshLayout != null){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<R>> loader) {
        adapter.clear();
    }


}
