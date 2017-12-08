package edu.virginia.cs.vmarketplace.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.AnalyticService;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.util.PushListenerService;
import edu.virginia.cs.vmarketplace.view.fragments.AbstractFragment;
import edu.virginia.cs.vmarketplace.view.fragments.HomeFragment;
import edu.virginia.cs.vmarketplace.view.fragments.MessageFragment;
import edu.virginia.cs.vmarketplace.view.fragments.SubscriptionFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ProfileFragment;
import edu.virginia.cs.vmarketplace.view.fragments.PublishFragment;
import edu.virginia.cs.vmarketplace.view.fragments.ViewPagerAdapter;

import static android.support.v4.content.LocalBroadcastManager.*;
import static edu.virginia.cs.vmarketplace.view.AppConstant.SWITCH_TAB;

/**
 * Created by cutehuazai on 11/21/17.
 */

public class MainActivity extends AppCompatActivity{
    public static String PACKAGE_NAME;

    public static PinpointManager pinpointManager;

    private AbstractFragment[] fragments;
    private int[] iconFill;

    private int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        position = 0;
        initFragments();
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < fragments.length;i++){
            tabLayout.getTabAt(i).setIcon(fragments[i].getIconResourceId());
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 2){
                    Intent startPublishActivityIntent = new Intent(MainActivity.this, PublishActivity.class);
                    startPublishActivityIntent.putExtra(AppConstant.PREVIOUS_TAB, position);
                    startActivity(startPublishActivityIntent);
                }else{
                    tab.setIcon(iconFill[tab.getPosition()]);
                }
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(fragments[tab.getPosition()].getIconResourceId());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();
        int tabIndex = 0;
        if (intent.hasExtra(SWITCH_TAB)) {
            tabIndex = intent.getExtras().getInt(SWITCH_TAB);
            tabLayout.getTabAt(tabIndex).select();
        }
        tabLayout.getTabAt(tabIndex).setIcon(iconFill[tabIndex]);
    }

    private void initFragments(){
        fragments = new AbstractFragment[5];
        fragments[0] = new HomeFragment();
        fragments[1] = new SubscriptionFragment();
        fragments[2] = new PublishFragment();
        fragments[3] = new MessageFragment();
        fragments[4] = new ProfileFragment();
        iconFill = new int[5];
        iconFill[0] = R.drawable.home_24p_fill;
        iconFill[1] = R.drawable.subscribe_24p_fill;
        iconFill[3] = R.drawable.message_24p_fill;
        iconFill[4] = R.drawable.user_24p_fill;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_PUSH_NOTIFICATION));
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
