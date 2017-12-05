package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class HomeFragment extends AbstractFragment {
    private AbstractFragment[] fragments;

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

        // setup view pager for tabs
        initFragments();
        ViewPager viewPager = rootView.findViewById(R.id.home_tab_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = rootView.findViewById(R.id.home_tab);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < fragments.length;i++){
            tabLayout.getTabAt(i).setIcon(fragments[i].getIconResourceId());
            tabLayout.getTabAt(i).setText(fragments[i].getTabName());
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initFragments() {
        fragments = new AbstractFragment[2];
        fragments[0] = new HomeTabNewFragment();
        fragments[1] = new HomeTabHotFragment();
    }
}
