package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class SubscriptionFragment extends AbstractFragment {
    public SubscriptionFragment(){
        super("subscription", R.drawable.subscribe_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place, container, false);
        getActivity().getWindow().setStatusBarColor(getTabBackground());
        return rootView;
    }

    public int getTabBackground(){
        return ContextCompat.getColor(getContext(), R.color.tan_background);
    }
}
