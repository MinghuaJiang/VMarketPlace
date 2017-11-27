package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class AppUser {
    private String username;
    private String userRating;
    private String userPic;
    public AppUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPic() {
        return userPic;
    }

    public String getUserRating(){
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
