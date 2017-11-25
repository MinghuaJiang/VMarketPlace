package edu.virginia.cs.vmarketplace.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class PublishFragment extends AbstractFragment{
    public PublishFragment(){
        super("profile_publish", R.drawable.add_24p);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_publish, container, false);
        return rootView;
    }
}
