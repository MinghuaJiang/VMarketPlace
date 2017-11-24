package edu.virginia.cs.vmarketplace.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class PlaceFragment extends AbstractFragment {
    public PlaceFragment(){
        super("place", R.drawable.ic_my_location_black_24dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place, container, false);
        return rootView;
    }
}
