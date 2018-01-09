package edu.virginia.cs.vmarketplace.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.virginia.cs.vmarketplace.R;
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
