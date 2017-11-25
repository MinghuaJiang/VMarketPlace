package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 11/24/17.
 */

public class User {
    private String username;
    private String userPic;

    public User(String username, String userPic) {
        this.username = username;
        this.userPic = userPic;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPic() {
        return userPic;
    }
}
