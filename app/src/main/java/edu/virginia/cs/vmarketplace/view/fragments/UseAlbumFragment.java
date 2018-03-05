package edu.virginia.cs.vmarketplace.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.util.CategoryUtil;
import edu.virginia.cs.vmarketplace.view.MainActivity;

/**
 * Created by cutehuazai on 11/25/17.
 */

public class UseAlbumFragment extends AbstractFragment {
    private Spinner spinner;
    public UseAlbumFragment(){
        super("Album", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View rootView = inflater.inflate(R.layout.publish_use_album, container, false);

        Button closeButton = rootView.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        spinner = rootView.findViewById(R.id.spinner_nav);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.category_item, CategoryUtil.getSubCategory("Second Hand"));
        spinner.setAdapter(spinnerAdapter);

        return rootView;
    }

    
}
