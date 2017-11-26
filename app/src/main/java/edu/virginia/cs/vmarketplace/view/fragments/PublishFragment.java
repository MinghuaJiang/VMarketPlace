package edu.virginia.cs.vmarketplace.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.view.PublishActivity;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class PublishFragment extends AbstractFragment{
    public PublishFragment(){
        super("publish", R.drawable.add_24p);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
