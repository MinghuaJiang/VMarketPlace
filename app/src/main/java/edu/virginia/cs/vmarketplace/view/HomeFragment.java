package edu.virginia.cs.vmarketplace.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class HomeFragment extends AbstractFragment {
    public HomeFragment() {
        super("home", R.drawable.ic_home_black_24dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        return rootView;
    }
}
