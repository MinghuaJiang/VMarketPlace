package edu.virginia.cs.vmarketplace.service.login;

import android.os.Bundle;

import com.amazonaws.mobile.auth.core.IdentityManager;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;

/**
 * Created by cutehuazai on 11/27/17.
 */

public class AppContext {
    private AppUser user;
    private IdentityManager manager;
    private String currentCategory;
    private boolean isPublish;
    private Bundle instanceState;
    private ProductItemsDO itemsDO;

    public AppContext(){
        manager = IdentityManager.getDefaultIdentityManager();
        instanceState = new Bundle();
    }

    public void loadContext(String userId){
        user = new AppUser(userId);
    }

    public void enrichUser(AppUserEnrichStrategy strategy){
        user.setUserName(strategy.getUserName());
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

    public Bundle getInstanceState(){
        return instanceState;
    }

    public void destroyInstanceState(){
        instanceState.clear();
    }

    public ProductItemsDO getItemsDO() {
        return itemsDO;
    }

    public void setItemsDO(ProductItemsDO itemsDO) {
        this.itemsDO = itemsDO;
    }
}
