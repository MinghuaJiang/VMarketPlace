package edu.virginia.cs.vmarketplace.service.login;

import android.net.Uri;

/**
 * Created by cutehuazai on 11/27/17.
 */

public interface AppUserEnrichStrategy {
    public String getUserName();
    public Uri getUserPicUri();
}
