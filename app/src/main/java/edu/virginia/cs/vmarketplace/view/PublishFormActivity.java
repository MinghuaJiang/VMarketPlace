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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
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
    private MyEditText titleView;
    private EditText descriptionView;
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView locationLogo;
    private TextView locationView;
    private MyEditText priceView;
    private Spinner spinner;
    private Location mLastKnowLocation;
    private Task<Location> locationTask;
    private SwipeRefreshLayout refreshLayout;
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

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setEnabled(false);
        titleView = findViewById(R.id.title);
        descriptionView = findViewById(R.id.description);
        priceView = findViewById(R.id.price);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowTitleEnabled(false);

        mFiles = getIntent().getStringArrayListExtra("image");
        String category = AppContextManager.getContextManager().getAppContext().getCurrentCategory();

        gridView = findViewById(R.id.container);

        locationLogo = findViewById(R.id.location_logo);

        locationView = findViewById(R.id.location);

        spinner = findViewById(R.id.category);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.category_item, getCategory(category));
        spinner.setAdapter(spinnerAdapter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        boolean isPublish = AppContextManager.getContextManager().getAppContext().isPublish();

        if(isPublish) {
            if (mFiles != null) {
                adapter = new ImageViewAdapter(this, new ArrayList<PreviewImageItem>());
                gridView.setAdapter(adapter);
                adapter.setmFiles(mFiles);
                getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
            } else {
                gridView.setVisibility(View.GONE);
            }
        }else{

        }

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
                        mLastKnowLocation = location;
                        startIntentService();
                    }
                }
            });
        }

        Button submitButton = findViewById(R.id.confirm_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleView.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.title_not_empty),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(priceView.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.price_not_empty),Toast.LENGTH_SHORT).show();
                    return;
                }

                refreshLayout.setRefreshing(true);
                String title = titleView.getText().toString();
                String description = descriptionView.getText().toString();
                String location = locationView.getText().toString();
                double price = Double.valueOf(priceView.getText().toString());
                String category = (String)spinner.getSelectedItem();
                Intent intent = new Intent(PublishFormActivity.this, PublishSuccessActivity.class);
                startActivity(intent);
            }
        });
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

    private List<String> getCategory(String category){
        List<String> result = new ArrayList();
        if(category.equals("Second Hand")) {
            result.add("Appliance");
            result.add("Bicycle");
            result.add("Book");
            result.add("Car");
            result.add("PC/Laptop");
            result.add("Digital Device");
            result.add("Furniture");
            result.add("Game Device");
            result.add("Kitchenware");
            result.add("Other");
        }else if(category.equals("Sublease")){
            result.add("Apartment");
            result.add("House");
        }else if(category.equals("Ride")){
            result.add("One-way Trip");
            result.add("Round Trip");
        }

        return result;
    }
}
