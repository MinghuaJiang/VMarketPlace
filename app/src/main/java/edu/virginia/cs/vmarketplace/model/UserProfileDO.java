package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Set;

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
    private Set<String> _boughtItems;
    private String _buyerRating;
    private String _buyerRatingCount;
    private String _department;
    private Set<String> _favoriteItems;
    private String _gender;
    private Set<String> _publishItems;
    private String _sellerRating;
    private String _sellerRatingCount;
    private Set<String> _soldItems;
    private Set<String> _thumbItems;
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
    @DynamoDBAttribute(attributeName = "boughtItems")
    public Set<String> getBoughtItems() {
        return _boughtItems;
    }

    public void setBoughtItems(final Set<String> _boughtItems) {
        this._boughtItems = _boughtItems;
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
    @DynamoDBAttribute(attributeName = "favoriteItems")
    public Set<String> getFavoriteItems() {
        return _favoriteItems;
    }

    public void setFavoriteItems(final Set<String> _favoriteItems) {
        this._favoriteItems = _favoriteItems;
    }
    @DynamoDBAttribute(attributeName = "gender")
    public String getGender() {
        return _gender;
    }

    public void setGender(final String _gender) {
        this._gender = _gender;
    }
    @DynamoDBAttribute(attributeName = "publishItems")
    public Set<String> getPublishItems() {
        return _publishItems;
    }

    public void setPublishItems(final Set<String> _publishItems) {
        this._publishItems = _publishItems;
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
    @DynamoDBAttribute(attributeName = "soldItems")
    public Set<String> getSoldItems() {
        return _soldItems;
    }

    public void setSoldItems(final Set<String> _soldItems) {
        this._soldItems = _soldItems;
    }
    @DynamoDBAttribute(attributeName = "thumbItems")
    public Set<String> getThumbItems() {
        return _thumbItems;
    }

    public void setThumbItems(final Set<String> _thumbItems) {
        this._thumbItems = _thumbItems;
    }
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return _userName;
    }

    public void setUserName(final String _userName) {
        this._userName = _userName;
    }
}
