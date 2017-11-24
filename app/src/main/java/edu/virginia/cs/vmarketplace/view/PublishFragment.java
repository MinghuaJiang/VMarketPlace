package edu.virginia.cs.vmarketplace.view;

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
        super("publish", R.drawable.ic_add_circle_outline_black_36dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publish, container, false);
        return rootView;
    }
}
