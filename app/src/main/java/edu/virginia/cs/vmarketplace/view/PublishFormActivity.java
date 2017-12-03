package edu.virginia.cs.vmarketplace.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.LocationConstant;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.service.FetchAddressIntentService;
import edu.virginia.cs.vmarketplace.view.fragments.ImageViewAdapter;
import edu.virginia.cs.vmarketplace.view.loader.PreviewImageItemLoader;

public class PublishFormActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PreviewImageItem>> {

    private AddressResultReceiver mResultReceiver;
    private GridView gridView;
    private ImageViewAdapter adapter;
    private List<String> mFiles;
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView locationLogo;
    private TextView locationView;
    private Location mLastKnowLocation;
    private Task<Location> locationTask;
    private static final int MY_LOCATION_REQUEST_CODE = 200;

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(LocationConstant.RECEIVER, mResultReceiver);
        intent.putExtra(LocationConstant.LOCATION_DATA_EXTRA, mLastKnowLocation);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultReceiver = new AddressResultReceiver(new Handler());
        setContentView(R.layout.activity_publish_form);
        Toolbar toolbar =
                findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Button closeBtn = findViewById(R.id.close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishFormActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowTitleEnabled(false);

        mFiles = getIntent().getStringArrayListExtra("image");
        gridView = findViewById(R.id.container);

        locationLogo = findViewById(R.id.location_logo);

        locationView = findViewById(R.id.location);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        adapter = new ImageViewAdapter(this, new ArrayList<PreviewImageItem>());
        gridView.setAdapter(adapter);
        adapter.setmFiles(mFiles);
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }else{
            locationTask =  mFusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null && Geocoder.isPresent()) {
                        // Logic to handle location object
                        System.out.println("hihihi");
                        mLastKnowLocation = location;
                        startIntentService();
                    }
                }
            });
        }
    }

    @Override
    public Loader<List<PreviewImageItem>> onCreateLoader(int id, Bundle args) {
        return new PreviewImageItemLoader(this, mFiles, 200);
    }

    @Override
    public void onLoadFinished(Loader<List<PreviewImageItem>> loader, List<PreviewImageItem> data) {
        adapter.clear();
        adapter.addAll(data);
        PreviewImageItem dummyItem = new PreviewImageItem(null, null);
        adapter.add(dummyItem);
    }

    @Override
    public void onLoaderReset(Loader<List<PreviewImageItem>> loader) {
        adapter.clear();
    }

    class AddressResultReceiver extends ResultReceiver {
        private String mAddressOutput;

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            if(resultCode == LocationConstant.SUCCESS_RESULT) {
                mAddressOutput = resultData.getString(LocationConstant.RESULT_DATA_KEY);
                locationView.setText(mAddressOutput);
            }else{
                locationLogo.setImageResource(R.drawable.location_blue);
                locationView.setText("Choose one location");
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationTask =  mFusedLocationClient.getLastLocation();
                    locationTask.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null && Geocoder.isPresent()) {
                                // Logic to handle location object
                                mLastKnowLocation = location;
                                startIntentService();
                            }
                        }
                    });
                }
            }
        }
    }
}
