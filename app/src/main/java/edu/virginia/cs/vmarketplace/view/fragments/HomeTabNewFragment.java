package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.view.loader.HomeNewTabLoader;
import edu.virginia.cs.vmarketplace.view.loader.ProductItemDOLoader;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeTabNewFragment extends AbstractFragment
        implements LoaderCallbacks<List<ProductItemsDO>>{

    private HomePostListAdapter homePostListAdapter;

    public HomeTabNewFragment() {
        super("new", R.drawable.new_icon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab_list, container, false);
        ListView listView = rootView.findViewById(R.id.home_tab_list);
        List<ProductItemsDO> test = new ArrayList<>();
        test.add(ProductItemsDO.getInstance());
        homePostListAdapter = new HomePostListAdapter(getActivity(),
                test);
        listView.setAdapter(homePostListAdapter);
        getLoaderManager().restartLoader(0, null, this).forceLoad();
        System.out.println("HomeTabNewFragment called");
        return rootView;
    }


    @Override
    public Loader<List<ProductItemsDO>> onCreateLoader(int id, Bundle args) {
        return new ProductItemDOLoader(getContext(), new HomeNewTabLoader());
    }

    @Override
    public void onLoadFinished(Loader<List<ProductItemsDO>> loader, List<ProductItemsDO> data) {
        homePostListAdapter.clear();
        homePostListAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ProductItemsDO>> loader) {

    }
}
