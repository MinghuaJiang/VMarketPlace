package edu.virginia.cs.vmarketplace.view.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/25/17.
 */

public class UseAlbumFragment extends AbstractFragment {
    public UseAlbumFragment(){
        super("Album", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View rootView = inflater.inflate(R.layout.publish_use_album, container, false);
        return rootView;
    }
}
