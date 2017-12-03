package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobile.auth.core.IdentityManager;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class AppContext {
    private AppUser user;
    private IdentityManager manager;
    private String currentCategory;
    private boolean isPublish;

    public AppContext(){
        manager = IdentityManager.getDefaultIdentityManager();
    }

    public void loadContext(String userId){
        user = new AppUser(userId);
    }

    public void enrichUser(AppUserEnrichStrategy strategy){
        user.setUserPic(strategy.getUserPic());
        user.setUserName(strategy.getUserName());
        user.setUserRating(strategy.getUserRating());
        user.setUserPicUri(strategy.getUserPicUri());
    }

    public AppUser getUser(){
        return user;
    }

    public void signOut(){
        manager.signOut();
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
    }
}
