package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.view.loader.HomeHotTabLoader;
import edu.virginia.cs.vmarketplace.view.loader.ProductItemDOLoader;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeTabHotFragment extends AbstractFragment
        implements LoaderManager.LoaderCallbacks<List<ProductItemsDO>> {

    private HomePostListAdapter homePostListAdapter;

    public HomeTabHotFragment() {
        super("hot", R.drawable.hot_icon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab_list, container, false);
        ListView listView = rootView.findViewById(R.id.home_tab_list);
        List<ProductItemsDO> test = new ArrayList<>();
        homePostListAdapter = new HomePostListAdapter(getActivity(),
                test);
        listView.setAdapter(homePostListAdapter);
        System.out.println("******" + homePostListAdapter.getCount());
        getLoaderManager().restartLoader(0, null, this).forceLoad();
        System.out.println("HomeTabHotFragment called");
        return rootView;
    }

    @Override
    public Loader<List<ProductItemsDO>> onCreateLoader(int id, Bundle args) {
        return new ProductItemDOLoader(getContext(), new HomeHotTabLoader());
    }

    @Override
    public void onLoadFinished(Loader<List<ProductItemsDO>> loader, List<ProductItemsDO> data) {
        homePostListAdapter.clear();
        homePostListAdapter.addAll(data);
        System.out.println("onLoadFinished called");
    }

    @Override
    public void onLoaderReset(Loader loader) {
        homePostListAdapter.clear();
    }
}
