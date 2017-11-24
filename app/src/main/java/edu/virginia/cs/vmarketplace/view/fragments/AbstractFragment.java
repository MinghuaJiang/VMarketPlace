package edu.virginia.cs.vmarketplace.view.fragments;


import android.support.v4.app.Fragment;

/**
 * Created by cutehuazai on 11/23/17.
 */

public abstract class AbstractFragment extends Fragment {
    private String tabName;
    private int iconResourceId;

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
}
