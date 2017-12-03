package edu.virginia.cs.vmarketplace.model;

import android.net.Uri;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class CognitoPoolEnrichStrategy implements AppUserEnrichStrategy {
    private String userId;
    public CognitoPoolEnrichStrategy(String userId){
        this.userId = userId;
    }

    @Override
    public String getUserName(){
        return userId;
    }

    @Override
    public String getUserPic() {
        return null;
    }

    @Override
    public String getUserRating() {
        return null;
    }

    @Override
    public Uri getUserPicUri(){
        return null;
    }
}
