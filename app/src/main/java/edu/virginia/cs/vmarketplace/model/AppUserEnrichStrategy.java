package edu.virginia.cs.vmarketplace.model;

import android.net.Uri;

/**
 * Created by cutehuazai on 11/27/17.
 */

public interface AppUserEnrichStrategy {
    public String getUserName();

    public String getUserPic();

    public Uri getUserPicUri();

    public String getUserRating();
}
