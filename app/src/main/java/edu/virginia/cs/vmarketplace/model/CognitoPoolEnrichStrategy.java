package edu.virginia.cs.vmarketplace.model;

import android.net.Uri;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class CognitoPoolEnrichStrategy implements AppUserEnrichStrategy {
    private String userId;
    private String userName;
    public CognitoPoolEnrichStrategy(String userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String getUserName(){
        return userName;
    }

    @Override
    public Uri getUserPicUri(){
        return null;
    }
}
