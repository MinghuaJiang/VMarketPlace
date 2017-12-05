package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonLoaderCallback;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeTabHotFragment extends AbstractFragment {

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
                new ArrayList<ProductItemsDO>());
        listView.setAdapter(homePostListAdapter);
        System.out.println("******" + homePostListAdapter.getCount());
        getLoaderManager().restartLoader(0, null, new CommonLoaderCallback<Void, ProductItemsDO>(
                homePostListAdapter, ProductItemService.getInstance()::findTop100HotPostsInOneWeek
        )).forceLoad();
        System.out.println("HomeTabHotFragment called");
        return rootView;
    }
}
