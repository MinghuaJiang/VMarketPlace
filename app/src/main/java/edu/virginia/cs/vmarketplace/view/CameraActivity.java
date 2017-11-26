package edu.virginia.cs.vmarketplace.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PlaceFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PublishFragment;
import edu.virginia.cs.vmarketplace.view.fragments.UseAlbumFragment;
import edu.virginia.cs.vmarketplace.view.fragments.UseCameraFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ViewPagerAdapter;

public class CameraActivity extends AppCompatActivity {
    private AbstractFragment[] fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initFragments();
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragments(){
        fragments = new AbstractFragment[2];
        fragments[0] = new PlaceFragment();
        fragments[1] = new ProfileFragment();
    }
}