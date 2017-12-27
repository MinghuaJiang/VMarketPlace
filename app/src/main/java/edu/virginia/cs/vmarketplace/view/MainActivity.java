package edu.virginia.cs.vmarketplace.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.AnalyticService;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.FetchAddressIntentService;
import edu.virginia.cs.vmarketplace.util.LocationHolder;
import edu.virginia.cs.vmarketplace.util.PushListenerService;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileFragment;
import edu.virginia.cs.vmarketplace.view.fragments.SubscriptionFragment;

import static android.support.v4.content.LocalBroadcastManager.getInstance;
import static edu.virginia.cs.vmarketplace.view.AppConstant.SWITCH_TAB;

/**
 * Created by cutehuazai on 11/21/17.
 */

public class MainActivity extends AppCompatActivity{
    public static String PACKAGE_NAME;

    private static String secondHandIdentifier = "Second Hand";
    private static String subleaseIdentifier = "Sublease";
    private static String ridesIdentifier = "Ride";
    private static String activitiesIdentifier = "Activity";

    public static PinpointManager pinpointManager;

    private AbstractFragment[] fragments;
    private int[] iconFill;

    private int position;

    private RelativeLayout homeContainer;
    private RelativeLayout placeContainer;
    private RelativeLayout messageContainer;
    private RelativeLayout profileContainer;
    private ImageView home;
    private ImageView place;
    private ImageView message;
    private ImageView profile;
    private ImageView publish;
    private RotateAnimation openRotate;
    private RotateAnimation closeRotate;
    private RotateAnimation closeRotateInPopup;
    private PopupWindow mPopupWindow;
    private LinearLayout popupContainer;
    private Animation slideIn;
    private Animation slideOut;
    private Animation slideDown;
    private Animation slideUp;
    private Animation slideBack;
    private AddressResultReceiver mResultReceiver;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastKnowLocation;
    private Task<Location> locationTask;
    private static final int MY_LOCATION_REQUEST_CODE = 200;

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(LocationConstant.RECEIVER, mResultReceiver);
        intent.putExtra(LocationConstant.LOCATION_DATA_EXTRA, mLastKnowLocation);
        startService(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultReceiver = new AddressResultReceiver(new Handler());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        pinpointManager = AWSClientFactory.getInstance().getPinpointManager(this);
        AnalyticService.getInstance().logNormalUsage(pinpointManager);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String deviceToken =
                            InstanceID.getInstance(MainActivity.this).getToken(
                                    "184697464235",
                                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);

                    pinpointManager.getNotificationClient().registerDeviceToken(deviceToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        PACKAGE_NAME = getApplicationContext().getPackageName();
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        initFragments();

        openRotate = new RotateAnimation(0.0f, 45.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        closeRotate = new RotateAnimation(45.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        closeRotateInPopup = new RotateAnimation(0.0f, -45.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        openRotate.setDuration(300);
        openRotate.setFillAfter(true);
        openRotate.setFillEnabled(true);

        closeRotate.setDuration(300);
        closeRotate.setFillAfter(true);
        closeRotate.setFillEnabled(true);

        closeRotateInPopup.setDuration(300);
        closeRotateInPopup.setFillAfter(true);
        closeRotateInPopup.setFillEnabled(true);

        slideIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in);
        slideOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_out);

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideBack = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_back);

        home = findViewById(R.id.home);
        place = findViewById(R.id.place);
        message = findViewById(R.id.message);
        profile = findViewById(R.id.profile);
        publish = findViewById(R.id.publish);

        homeContainer = findViewById(R.id.home_container);
        placeContainer = findViewById(R.id.place_container);
        messageContainer = findViewById(R.id.message_container);
        profileContainer = findViewById(R.id.profile_container);

        popupContainer = findViewById(R.id.popup_container);

        homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                onTabChange();
            }
        });

        placeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 1;
                onTabChange();
            }
        });

        messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 2;
                onTabChange();
            }
        });

        profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 3;
                onTabChange();
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(openRotate);
                showPopup();
            }
        });

        Intent intent = getIntent();
        position = 0;
        if (intent.hasExtra(SWITCH_TAB)) {
            position = intent.getExtras().getInt(SWITCH_TAB);
        }
        onTabChange();
    }

    private void showPopup(){
        // Inflate the custom layout/view
        View customView = LayoutInflater.from(this).inflate(R.layout.popup_publish, null);

        mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.separator));
        if(Build.VERSION.SDK_INT >= 23){
            mPopupWindow.setElevation(5.0f);
            mPopupWindow.setEnterTransition(new Fade());
            mPopupWindow.setExitTransition(new Fade());
        }

        final AppContext context = AppContextManager.getContextManager().getAppContext();
        // second hand logic begins here

        ConstraintLayout constraintLayout = customView.findViewById(R.id.container);
        constraintLayout.setAlpha(0.92f);

        ImageView secondHand = customView.findViewById(R.id.second_hand_image);
        secondHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(MainActivity.this, PhotoActivity.class);
                context.setCurrentCategory(secondHandIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // sublease logic begins here
        ImageView sublease = customView.findViewById(R.id.sublease_image);
        sublease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(MainActivity.this, PhotoActivity.class);
                context.setCurrentCategory(subleaseIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // rides logic begins here
        ImageView rides = customView.findViewById(R.id.rides_image);
        rides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(MainActivity.this, PublishFormActivity.class);
                context.setCurrentCategory(ridesIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // activities logic begins here
        ImageView activities = customView.findViewById(R.id.activity_image);
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent useCameraIntent = new Intent(MainActivity.this, PhotoActivity.class);
                context.setCurrentCategory(activitiesIdentifier);
                context.setPublish(true);
                startActivity(useCameraIntent);
            }
        });

        // Get a reference for the custom view close button
        ImageView closeButton = customView.findViewById(R.id.close_btn);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondHand.startAnimation(slideOut);
                rides.startAnimation(slideOut);
                sublease.startAnimation(slideOut);
                activities.startAnimation(slideOut);
                closeButton.startAnimation(closeRotateInPopup);
                publish.startAnimation(closeRotate);
                slideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mPopupWindow.dismiss();
                        getWindow().setStatusBarColor(fragments[position].getTabBackground());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        mPopupWindow.showAtLocation(popupContainer, Gravity.CENTER,0,0);
        secondHand.startAnimation(slideIn);
        rides.startAnimation(slideIn);
        sublease.startAnimation(slideIn);
        activities.startAnimation(slideIn);
        slideIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                secondHand.startAnimation(slideDown);
                rides.startAnimation(slideDown);
                sublease.startAnimation(slideDown);
                activities.startAnimation(slideDown);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                secondHand.startAnimation(slideUp);
                rides.startAnimation(slideUp);
                sublease.startAnimation(slideUp);
                activities.startAnimation(slideUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                secondHand.startAnimation(slideBack);
                rides.startAnimation(slideBack);
                sublease.startAnimation(slideBack);
                activities.startAnimation(slideBack);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void onTabChange() {
        if(position == 0) {
            home.setImageResource(fragments[0].getIconResourceId());
            place.setImageResource(fragments[1].getIconResourceId());
            message.setImageResource(fragments[2].getIconResourceId());
            profile.setImageResource(fragments[3].getIconResourceId());
            home.setImageResource(iconFill[0]);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.page_container, fragments[0]).commit();
        }else if(position == 1){
            home.setImageResource(fragments[0].getIconResourceId());
            place.setImageResource(fragments[1].getIconResourceId());
            message.setImageResource(fragments[2].getIconResourceId());
            profile.setImageResource(fragments[3].getIconResourceId());
            place.setImageResource(iconFill[1]);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.page_container, fragments[1]).commit();
        }else if(position == 2){
            home.setImageResource(fragments[0].getIconResourceId());
            place.setImageResource(fragments[1].getIconResourceId());
            message.setImageResource(fragments[2].getIconResourceId());
            profile.setImageResource(fragments[3].getIconResourceId());
            message.setImageResource(iconFill[2]);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.page_container, fragments[2]).commit();
        }else{
            home.setImageResource(fragments[0].getIconResourceId());
            place.setImageResource(fragments[1].getIconResourceId());
            message.setImageResource(fragments[2].getIconResourceId());
            profile.setImageResource(fragments[3].getIconResourceId());
            profile.setImageResource(iconFill[3]);
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.page_container, fragments[3]).commit();
        }
    }

    private void initFragments(){
        fragments = new AbstractFragment[4];
        fragments[0] = new HomeFragment();
        fragments[1] = new SubscriptionFragment();
        fragments[2] = new MessageFragment();
        fragments[3] = new ProfileFragment();
        iconFill = new int[4];
        iconFill[0] = R.drawable.home_24p_fill;
        iconFill[1] = R.drawable.subscribe_24p_fill;
        iconFill[2] = R.drawable.message_24p_fill;
        iconFill[3] = R.drawable.user_24p_fill;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    extractLocation();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void extractLocation() {
        locationTask =  mFusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null && Geocoder.isPresent()) {
                    // Logic to handle location object
                    mLastKnowLocation = location;
                    LocationHolder.getInstance().setLatitude(mLastKnowLocation.getLatitude());
                    LocationHolder.getInstance().setLongitude(mLastKnowLocation.getLongitude());
                    LocationHolder.getInstance().setLocation(null);
                    startIntentService();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }else{
            extractLocation();
        }
        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_PUSH_NOTIFICATION));
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
                 LocationHolder.getInstance().setLocation(resultData.getString(LocationConstant.RESULT_DATA_KEY));
            }
        }
    }

    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle data = intent.getBundleExtra(PushListenerService.INTENT_SNS_NOTIFICATION_DATA);
            String message = PushListenerService.getMessage(data);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Push notification")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    };


}
