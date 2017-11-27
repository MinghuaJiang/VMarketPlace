package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobile.auth.core.IdentityManager;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class AppContext {
    private AppUser user;
    private IdentityManager manager;
    public AppContext(){
        manager = IdentityManager.getDefaultIdentityManager();
    }

    public void loadContext(String userId){
        user = new AppUser(userId);
    }

    public void enrichUser(AppUserEnrichStrategy strategy){
        user.setUserPic(strategy.getUserPic());
        user.setUserRating(strategy.getUserRating());
    }

    public AppUser getUser(){
        return user;
    }

    public void signOut(){
        manager.signOut();
    }
}
