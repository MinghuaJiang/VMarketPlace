package edu.virginia.cs.vmarketplace.model;

import android.net.Uri;

import com.squareup.picasso.Picasso;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class AppUser {
    private String userId;
    private String userName;
    private String userRating;
    private String userPic;
    private Uri userPicUri;

    public AppUser(String userId) {
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public String getUserRating(){
        return userRating;
    }

    public Uri getUserPicUri() {
        return userPicUri;
    }

    public void setUserPicUri(Uri userPicUri) {
        this.userPicUri = userPicUri;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
