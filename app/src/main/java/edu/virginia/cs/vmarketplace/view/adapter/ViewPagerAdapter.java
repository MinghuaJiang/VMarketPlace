package edu.virginia.cs.vmarketplace.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private AbstractFragment[] fragments;

    public ViewPagerAdapter(FragmentManager fm, AbstractFragment[] fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return fragments[position].getTabName();
    }
}
