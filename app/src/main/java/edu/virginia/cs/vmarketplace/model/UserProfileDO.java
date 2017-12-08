package edu.virginia.cs.vmarketplace.model;

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
    private String _address;
    private String _avatar;
    private String _backgroundPic;
    private String _bio;
    private String _birthdate;
    private String _buyerRating;
    private String _buyerRatingCount;
    private String _department;
    private String _sellerRating;
    private String _sellerRatingCount;
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
    @DynamoDBAttribute(attributeName = "bio")
    public String getBio() {
        return _bio;
    }

    public void setBio(final String _bio) {
        this._bio = _bio;
    }
    @DynamoDBAttribute(attributeName = "birthdate")
    public String getBirthdate() {
        return _birthdate;
    }

    public void setBirthdate(final String _birthdate) {
        this._birthdate = _birthdate;
    }
    @DynamoDBAttribute(attributeName = "buyerRating")
    public String getBuyerRating() {
        return _buyerRating;
    }

    public void setBuyerRating(final String _buyerRating) {
        this._buyerRating = _buyerRating;
    }
    @DynamoDBAttribute(attributeName = "buyerRatingCount")
    public String getBuyerRatingCount() {
        return _buyerRatingCount;
    }

    public void setBuyerRatingCount(final String _buyerRatingCount) {
        this._buyerRatingCount = _buyerRatingCount;
    }
    @DynamoDBAttribute(attributeName = "department")
    public String getDepartment() {
        return _department;
    }

    public void setDepartment(final String _department) {
        this._department = _department;
    }
    @DynamoDBAttribute(attributeName = "sellerRating")
    public String getSellerRating() {
        return _sellerRating;
    }

    public void setSellerRating(final String _sellerRating) {
        this._sellerRating = _sellerRating;
    }
    @DynamoDBAttribute(attributeName = "sellerRatingCount")
    public String getSellerRatingCount() {
        return _sellerRatingCount;
    }

    public void setSellerRatingCount(final String _sellerRatingCount) {
        this._sellerRatingCount = _sellerRatingCount;
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

    @Override
    public String toString() {
        return "UserProfileDO{" +
                "_userId='" + _userId + '\'' +
                ", _address='" + _address + '\'' +
                ", _avatar='" + _avatar + '\'' +
                ", _backgroundPic='" + _backgroundPic + '\'' +
                ", _bio='" + _bio + '\'' +
                ", _birthdate='" + _birthdate + '\'' +
                ", _buyerRating='" + _buyerRating + '\'' +
                ", _buyerRatingCount='" + _buyerRatingCount + '\'' +
                ", _department='" + _department + '\'' +
                ", _sellerRating='" + _sellerRating + '\'' +
                ", _sellerRatingCount='" + _sellerRatingCount + '\'' +
                ", _sex='" + _sex + '\'' +
                ", _userName='" + _userName + '\'' +
                '}';
    }
}
