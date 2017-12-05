package edu.virginia.cs.vmarketplace.model.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/4/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-USER_PROFILE")
public class UserProfileDO {
    private String _userId;
    private Double _userRating;
    private String _avatar;
    private String _userName;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }

    @DynamoDBRangeKey(attributeName = "userRating")
    @DynamoDBAttribute(attributeName = "userRating")
    public Double getUserRating() {
        return _userRating;
    }

    public void setUserRating(final Double _userRating) {
        this._userRating = _userRating;
    }

    @DynamoDBAttribute(attributeName = "avatar")
    public String getAvatar() {
        return _avatar;
    }

    public void setAvatar(final String _avatar) {
        this._avatar = _avatar;
    }
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return _userName;
    }

    public void setUserName(final String _userName) {
        this._userName = _userName;
    }
}
