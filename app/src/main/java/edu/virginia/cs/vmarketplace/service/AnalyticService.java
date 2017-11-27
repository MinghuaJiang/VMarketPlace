package edu.virginia.cs.vmarketplace.service;

import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class AnalyticService {
    private static AnalyticService service = new AnalyticService();

    private AnalyticService(){

    }

    public static AnalyticService getInstance(){
        return service;
    }

    public void logNormalUsage(PinpointManager pinpointManager){
        pinpointManager.getSessionClient().startSession();

        // Stop the session and submit the default app started event
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();
    }

    public void logEvent(PinpointManager pinpointManager, AnalyticsEvent event){
        pinpointManager.getSessionClient().startSession();
        pinpointManager.getAnalyticsClient().recordEvent(event);
        pinpointManager.getSessionClient().stopSession();
        pinpointManager.getAnalyticsClient().submitEvents();
    }
}
