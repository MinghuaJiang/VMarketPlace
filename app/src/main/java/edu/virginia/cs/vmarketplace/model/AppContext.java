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
    public AppUser getUser(){
        return user;
    }

    public void signOut(){
        manager.signOut();
    }
}
