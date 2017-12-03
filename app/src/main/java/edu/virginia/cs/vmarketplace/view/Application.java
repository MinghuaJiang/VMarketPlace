package edu.virginia.cs.vmarketplace.view;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;

import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class Application extends MultiDexApplication {
    private static PinpointManager pinpointManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static PinpointManager getPinpointManager(Context context){
        if(pinpointManager == null){
            pinpointManager = AWSClientFactory.getInstance().getPinpointManager(context);
        }
        return pinpointManager;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
