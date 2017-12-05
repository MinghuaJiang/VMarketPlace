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
    private String _userName;
    private String _address;
    private String _avatar;
    private String _bio;
    private String _birthdate;
    private Double _buyerRating;
    private Double _buyerRatingCount;
    private String _department;
    private Double _sellerRating;
    private Double _sellerRatingCount;
    private String _sex;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "userName")
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return _userName;
    }

    public void setUserName(final String _userName) {
        this._userName = _userName;
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
    public Double getBuyerRating() {
        return _buyerRating;
    }

    public void setBuyerRating(final Double _buyerRating) {
        this._buyerRating = _buyerRating;
    }
    @DynamoDBAttribute(attributeName = "buyerRatingCount")
    public Double getBuyerRatingCount() {
        return _buyerRatingCount;
    }

    public void setBuyerRatingCount(final Double _buyerRatingCount) {
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
    public Double getSellerRating() {
        return _sellerRating;
    }

    public void setSellerRating(final Double _sellerRating) {
        this._sellerRating = _sellerRating;
    }
    @DynamoDBAttribute(attributeName = "sellerRatingCount")
    public Double getSellerRatingCount() {
        return _sellerRatingCount;
    }

    public void setSellerRatingCount(final Double _sellerRatingCount) {
        this._sellerRatingCount = _sellerRatingCount;
    }
    @DynamoDBAttribute(attributeName = "sex")
    public String getSex() {
        return _sex;
    }

    public void setSex(final String _sex) {
        this._sex = _sex;
    }
}
