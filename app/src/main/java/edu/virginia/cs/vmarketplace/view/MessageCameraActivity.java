package edu.virginia.cs.vmarketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.view.fragments.UseCameraFragment;

public class MessageCameraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_camera);
        final UseCameraFragment fragment = new UseCameraFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        fragment.setPicLimit(10);
        fragment.setRequestCode(UseCameraFragment.REQUEST_FROM_MESSAGE);
    }
}
