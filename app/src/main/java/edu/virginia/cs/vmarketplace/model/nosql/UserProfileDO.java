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
    private String _address;
    private String _avatar;
    private String _backgroundPic;
    private String _birthdate;
    private String _department;
    private String _selfIntro;
    private String _sex;
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
    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return _address;
    }

    public void setAddress(final String _address) {
        this._address = _address;
    }
    @DynamoDBAttribute(attributeName = "avatar")
    public String getAvatar() {
        return _avatar;
    }

    public void setAvatar(final String _avatar) {
        this._avatar = _avatar;
    }
    @DynamoDBAttribute(attributeName = "background_pic")
    public String getBackgroundPic() {
        return _backgroundPic;
    }

    public void setBackgroundPic(final String _backgroundPic) {
        this._backgroundPic = _backgroundPic;
    }
    @DynamoDBAttribute(attributeName = "birthdate")
    public String getBirthdate() {
        return _birthdate;
    }

    public void setBirthdate(final String _birthdate) {
        this._birthdate = _birthdate;
    }
    @DynamoDBAttribute(attributeName = "department")
    public String getDepartment() {
        return _department;
    }

    public void setDepartment(final String _department) {
        this._department = _department;
    }
    @DynamoDBAttribute(attributeName = "self_intro")
    public String getSelfIntro() {
        return _selfIntro;
    }

    public void setSelfIntro(final String _selfIntro) {
        this._selfIntro = _selfIntro;
    }
    @DynamoDBAttribute(attributeName = "sex")
    public String getSex() {
        return _sex;
    }

    public void setSex(final String _sex) {
        this._sex = _sex;
    }
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return _userName;
    }

    public void setUserName(final String _userName) {
        this._userName = _userName;
    }
}
