package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeTabHotFragment extends AbstractFragment {
    public HomeTabHotFragment() {
        super("hot", R.drawable.hot_icon);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
