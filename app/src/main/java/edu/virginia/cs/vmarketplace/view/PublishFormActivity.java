package edu.virginia.cs.vmarketplace.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
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

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.AppConstant;
import edu.virginia.cs.vmarketplace.model.AppContext;
import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.LocationConstant;
import edu.virginia.cs.vmarketplace.model.PreviewImageItem;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.FetchAddressIntentService;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;
import edu.virginia.cs.vmarketplace.view.fragments.ImageViewAdapter;
import edu.virginia.cs.vmarketplace.view.loader.PreviewImageItemLoader;

public class PublishFormActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PreviewImageItem>> {
    private static final String TITLE_CONTENT = "title";
    private static final String DESCRIPTION_CONTENT = "description";
    private static final String PRICE_CONTENT = "price";
    private static final String CATEGORY_CONTENT = "category";

    private AppContext appContext;
    private AddressResultReceiver mResultReceiver;
    private DynamoDBMapper mapper;
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
    private String category;
    private static final int MY_LOCATION_REQUEST_CODE = 200;

    private int count;

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
        count = 0;
        appContext = AppContextManager.getContextManager().getAppContext();
        Toolbar toolbar =
                findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Button closeBtn = findViewById(R.id.close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appContext.destroyInstanceState();
                appContext.setItemsDO(null);
                Intent fromIntent = getIntent();
                int jump_from = fromIntent.getIntExtra(AppConstant.JUMP_FROM, 0);
                if(jump_from == AppConstant.PUBLISH_BY_ME){
                    Intent intent = new Intent(PublishFormActivity.this, ProfilePublishActivity.class);
                    startActivity(intent);
                }else if(jump_from == AppConstant.PHOTO_ACTIVITY){
                    Intent intent = new Intent(PublishFormActivity.this, MainActivity.class);
                    startActivity(intent);
                }
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

        mapper = AWSClientFactory.getInstance().getDBMapper();

        gridView = findViewById(R.id.container);

        locationLogo = findViewById(R.id.location_logo);

        locationView = findViewById(R.id.location);

        spinner = findViewById(R.id.category);

        boolean isPublish = appContext.isPublish();

        if(isPublish) {
            mFiles = getIntent().getStringArrayListExtra("image");
            if (mFiles != null) {
                adapter = new ImageViewAdapter(this, new ArrayList<PreviewImageItem>());
                gridView.setAdapter(adapter);
                adapter.setmFiles(mFiles);
                getSupportLoaderManager().restartLoader(0, null, this).forceLoad();
            } else {
                gridView.setVisibility(View.GONE);
            }

            category = appContext.getCurrentCategory();
            ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.category_item, getSubCategory(category));
            spinner.setAdapter(spinnerAdapter);
        }else{
            ProductItemsDO itemsDO = appContext.getItemsDO();
            mFiles = itemsDO.getOriginalFiles();
            titleView.setText(itemsDO.getTitle());
            descriptionView.setText(itemsDO.getDescription());
            priceView.setText(String.valueOf(itemsDO.getPrice()));

            category = itemsDO.getCategory();
            appContext.setCurrentCategory(category);
            ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.category_item, getSubCategory(category));
            spinner.setAdapter(spinnerAdapter);
            spinner.setSelection(itemsDO.getSubcategoryPosition().intValue());
            if (mFiles != null) {
                adapter = new ImageViewAdapter(this, new ArrayList<PreviewImageItem>());
                gridView.setAdapter(adapter);
                adapter.setmFiles(mFiles);
                getSupportLoaderManager().restartLoader(0, null, PublishFormActivity.this).forceLoad();
                final TransferUtility utility = AWSClientFactory.getInstance().getTransferUtility(getApplicationContext());
                for(int i = 0;i < mFiles.size();i++){
                    utility.download(itemsDO.getPics().get(i), new File(mFiles.get(i)), new TransferListener(){
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            if(state == TransferState.COMPLETED){
                                count++;
                                if(count == mFiles.size()){
                                    getSupportLoaderManager().restartLoader(0, null, PublishFormActivity.this).forceLoad();
                                }
                            }
                        }

                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                        }

                        @Override
                        public void onError(int id, Exception ex) {

                        }
                    });
                }
            } else {
                gridView.setVisibility(View.GONE);
            }
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

        if(!appContext.getInstanceState().isEmpty()) {
            titleView.setText(appContext.getInstanceState().getString(TITLE_CONTENT));
            descriptionView.setText(appContext.getInstanceState().getString(DESCRIPTION_CONTENT));
            priceView.setText(appContext.getInstanceState().getString(PRICE_CONTENT));
            spinner.setSelection(appContext.getInstanceState().getInt(CATEGORY_CONTENT));
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
                String location = locationView.getText().toString();
                String description = descriptionView.getText().toString();
                double price = Double.valueOf(priceView.getText().toString());
                String subCategory = (String)spinner.getSelectedItem();

                ProductItemsDO productItemsDo = appContext.getItemsDO();
                if(productItemsDo == null){
                    productItemsDo = new ProductItemsDO();
                    productItemsDo.setItemId(UUID.randomUUID().toString());
                    productItemsDo.setUserId(AppContextManager.getContextManager().getAppContext().getUser().getUserId());
                    productItemsDo.setViewCount(0.0);
                    productItemsDo.setReplyCount(0.0);
                }

                productItemsDo.setCategory(category);
                productItemsDo.setSubcategory(subCategory);
                productItemsDo.setPrice(price);
                productItemsDo.setTitle(title);
                productItemsDo.setSubcategoryPosition(Double.valueOf(spinner.getSelectedItemPosition()));
                productItemsDo.setOriginalFiles(mFiles);
                productItemsDo.setDescription(description);
                productItemsDo.setLatitude(mLastKnowLocation.getLatitude());
                productItemsDo.setLongtitude(mLastKnowLocation.getLongitude());
                productItemsDo.setLocationName(location);
                productItemsDo.setModificationTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                appContext.destroyInstanceState();
                appContext.setItemsDO(null);
                loadIntoS3(productItemsDo);
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

    public void saveState(){
        appContext.getInstanceState().putString(TITLE_CONTENT, titleView.getText().toString());
        appContext.getInstanceState().putString(DESCRIPTION_CONTENT, descriptionView.getText().toString());
        appContext.getInstanceState().putString(PRICE_CONTENT, priceView.getText().toString());
        appContext.getInstanceState().putInt(CATEGORY_CONTENT, spinner.getSelectedItemPosition());
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

    private void loadIntoS3(final ProductItemsDO itemDo){
        final List<String> result = new ArrayList<>();
        final TransferUtility utility = AWSClientFactory.getInstance().getTransferUtility(getApplicationContext());
        final List<String> count = new ArrayList<>();
        for(String each: mFiles){
            final File file = new File(each);
            final String key = "public" + "/" + file.getName();
            result.add(key);

            TransferObserver observer = utility.upload(key, file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if(state == TransferState.COMPLETED){
                        file.delete();
                        count.add(key);
                        if(count.size() == mFiles.size()){
                            itemDo.setPics(result);
                            itemDo.setThumbPic(result.get(0));
                            insertToDB(itemDo);
                        }
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                }

                @Override
                public void onError(int id, Exception ex) {

                }
            });
        }
    }

    private void insertToDB(ProductItemsDO productItemsDO){
        new ProductItemInsertTask(refreshLayout, mapper, this).execute(productItemsDO);
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

    private List<String> getSubCategory(String category){
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

    static class ProductItemInsertTask extends AsyncTask<ProductItemsDO,Void, Void>{
        private SwipeRefreshLayout refreshLayout;
        private DynamoDBMapper mapper;
        private PublishFormActivity activity;

        public ProductItemInsertTask(SwipeRefreshLayout refreshLayout, DynamoDBMapper mapper, PublishFormActivity activity){
            this.refreshLayout = refreshLayout;
            this.mapper = mapper;
            this.activity = activity;
        }
        @Override
        protected Void doInBackground(ProductItemsDO... productItemsDOS) {
            mapper.save(productItemsDOS[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            refreshLayout.setRefreshing(false);
            Intent intent = new Intent(activity, PublishSuccessActivity.class);
            activity.startActivity(intent);
        }
    }
}
