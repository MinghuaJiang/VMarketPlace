package edu.virginia.cs.vmarketplace.view.login;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by VINCENTWEN on 11/24/17.
 */

public class LoginHelper {

    public static LoginHelper loginHelper;

    public static CognitoUserPool userPool;
    
    /**
     * Add your pool id here
     */
    private static final String userPoolId = "us-east-1_ePFgGYG7L";

    /**
     * Add you app id
     */
    private static final String clientId = "3mao7kf3v52faputddls9oq9lj";

    /**
     * App secret associated with your app id - if the App id does not have an associated App secret,
     * set the App secret to null.
     * e.g. clientSecret = null;
     */
    private static final String clientSecret = "q3r3bb008cc9v3tsej3a8rtvjk2jqqeuaq2lta9gciooqmgj8lc";

    /**
     * Set Your AppUser Pools region.
     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
     */
    private static final Regions cognitoRegion = Regions.US_EAST_1;

    // AppUser details from the service
    private static CognitoUserSession currSession;
    private static CognitoUserDetails userDetails;

    // AppUser details to display - they are the current values, including any local modification
    private static boolean phoneVerified;
    private static boolean emailVerified;

    private static boolean phoneAvailable;
    private static boolean emailAvailable;

    private static Set<String> currUserAttributes;

    public static void init(Context context) {
        if (loginHelper != null && userPool != null) {
            return;
        }

        if (loginHelper == null) {
            loginHelper = new LoginHelper();
        }

        if (userPool == null) {

            // Create a user pool with default ClientConfiguration
            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);

            // This will also work
            /*
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            AmazonCognitoIdentityProvider cipClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
            cipClient.setRegion(Region.getRegion(cognitoRegion));
            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cipClient);
            */

        }

//        phoneVerified = false;
//        phoneAvailable = false;
//        emailVerified = false;
//        emailAvailable = false;

//        currUserAttributes = new HashSet<String>();
//        currDisplayedItems = new ArrayList<ItemToDisplay>();
//        trustedDevices = new ArrayList<ItemToDisplay>();
//        firstTimeLogInDetails = new ArrayList<ItemToDisplay>();
//        firstTimeLogInUpDatedAttributes= new HashMap<String, String>();
//
//        newDevice = null;
//        thisDevice = null;
//        thisDeviceTrustState = false;
    }
}
