package edu.virginia.cs.vmarketplace.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.virginia.cs.vmarketplace.R;

/**
 * Created by cutehuazai on 11/23/17.
 */

public class MessageFragment extends AbstractFragment{
    public MessageFragment(){
        super("message", R.drawable.ic_message_black_24dp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message, container, false);
        return rootView;
    }
}
