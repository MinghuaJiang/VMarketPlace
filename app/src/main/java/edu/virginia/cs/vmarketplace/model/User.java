package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class User {
    private String username;
    private String userRating;
    private String userPic;

    public User(String username, String userPic, String userRating) {
        this.username = username;
        this.userPic = userPic;
        this.userRating = userRating;
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
}
