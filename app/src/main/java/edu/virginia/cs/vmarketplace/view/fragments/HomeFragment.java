package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;

import java.util.ArrayList;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.ProductItemService;
import edu.virginia.cs.vmarketplace.service.loader.CommonRecycleViewLoaderCallback;
import edu.virginia.cs.vmarketplace.view.adapter.HomePageListAdapter;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class HomeFragment extends AbstractFragment {
    private HomePageListAdapter homeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TabLayout tabLayoutFixed;

    private int currentVisiblePosition1;
    private int currentVisiblePosition2;

    public HomeFragment() {
        super("home", R.drawable.home_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        Toolbar toolbar =
                rootView.findViewById(R.id.home_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab =  ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);

        getActivity().getWindow().setStatusBarColor(
                getTabBackground());

        tabLayoutFixed = rootView.findViewById(R.id.tab_fixed);

        TabLayout.Tab tabNewFixed = tabLayoutFixed.newTab();

        tabNewFixed.setText("Latest");

        TabLayout.Tab tabNearByFixed = tabLayoutFixed.newTab();

        tabNearByFixed.setText("NearBy");

        tabLayoutFixed.addTab(tabNewFixed);

        tabLayoutFixed.addTab(tabNearByFixed);

        tabLayoutFixed.getTabAt(0).select();

        recyclerView = rootView.findViewById(R.id.home_tab_list);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        homeAdapter = new HomePageListAdapter(getContext(),
                new ArrayList<>(), 0, this);

        recyclerView.setAdapter(homeAdapter);
        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(tabLayoutFixed.getTabAt(0).isSelected()) {
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() != -1){
                        currentVisiblePosition1 = layoutManager.findFirstCompletelyVisibleItemPosition();
                    }
                }else{
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() != -1) {
                        currentVisiblePosition2 = layoutManager.findFirstCompletelyVisibleItemPosition();
                    }
                }
                if(layoutManager.findFirstCompletelyVisibleItemPosition() > 1 || layoutManager.findFirstCompletelyVisibleItemPosition() == -1){
                    tabLayoutFixed.setVisibility(View.VISIBLE);
                }else{
                    tabLayoutFixed.setVisibility(View.GONE);
                }
            }
        });

        getLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<Void, ProductItemsDO>(
                getContext(),
                homeAdapter,
                ProductItemService.getInstance()::findTop100NewPostsInOneWeek
        ).with((x)->
                homeAdapter.setData(x, 0))).forceLoad();

        tabLayoutFixed.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<Void, ProductItemsDO>(
                            getContext(),
                            homeAdapter,
                            ProductItemService.getInstance()::findTop100NewPostsInOneWeek
                    ).with((x)->
                            homeAdapter.setData(x, 2))).forceLoad();
                    if(currentVisiblePosition2 > 1){
                        layoutManager.scrollToPositionWithOffset(2, 130);
                    }
                }else{
                    getLoaderManager().restartLoader(0, null, new CommonRecycleViewLoaderCallback<Void, ProductItemsDO>(
                            getContext(),
                            homeAdapter,
                            ProductItemService.getInstance()::findTop100HotPostsInOneWeek
                    ).with((x)->
                    homeAdapter.setData(x, 2))).forceLoad();
                    System.out.println(currentVisiblePosition1);
                    if(currentVisiblePosition1 > 1){
                        layoutManager.scrollToPositionWithOffset(2, 130);
                    }
                }
                homeAdapter.getTabViewHolder().tabLayout.getTabAt(tab.getPosition()).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayoutFixed.setVisibility(View.GONE);
        return rootView;
    }

    public int getTabBackground(){
        return ContextCompat.getColor(getContext(), R.color.barBackground);
    }

    public void setOnTabListener(TabLayout.Tab tab){
        tabLayoutFixed.getTabAt(tab.getPosition()).select();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        if(homeAdapter.getHeaderViewHolder() != null) {
            homeAdapter.getHeaderViewHolder().stopAutoCycle();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeAdapter.getHeaderViewHolder() != null) {
            homeAdapter.getHeaderViewHolder().startAutoCycle();
        }
    }
}
