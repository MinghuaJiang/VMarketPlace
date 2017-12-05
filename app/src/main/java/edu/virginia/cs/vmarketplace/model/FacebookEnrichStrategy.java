package edu.virginia.cs.vmarketplace.model;

import android.net.Uri;

import com.facebook.Profile;

/**
 * Created by cutehuazai on 12/2/17.
 */

public class FacebookEnrichStrategy implements AppUserEnrichStrategy {
    @Override
    public String getUserName(){
        return Profile.getCurrentProfile().getName();
    }

    @Override
    public String getUserPic() {
        return null;
    }

    @Override
    public Uri getUserPicUri() {
        return Profile.getCurrentProfile().getProfilePictureUri(160,160);
    }

    @Override
    public String getUserRating() {
        return null;
    }
}