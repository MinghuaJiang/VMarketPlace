package edu.virginia.cs.vmarketplace.service.login;

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
    public Uri getUserPicUri() {
        return Profile.getCurrentProfile().getProfilePictureUri(160,160);
    }
}
