package edu.virginia.cs.vmarketplace.view.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by cutehuazai on 11/23/17.
 */

public abstract class AbstractFragment extends Fragment {
    private String tabName;
    private int iconResourceId;
    private boolean isRefreshed;

    public AbstractFragment(){
    }

    public AbstractFragment(String tabName, int iconResourceId){
        this.tabName = tabName;
        this.iconResourceId = iconResourceId;
    }

    public String getTabName() {
        return tabName;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public int getTabBackground(){
        return 0;
    }

    public void refresh(){
        isRefreshed = true;
    }

    public boolean isRefreshed() {
        return isRefreshed;
    }
}
