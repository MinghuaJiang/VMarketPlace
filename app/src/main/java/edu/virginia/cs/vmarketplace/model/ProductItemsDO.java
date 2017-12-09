package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-PRODUCT_ITEMS")

public class ProductItemsDO {
    private String _itemId;
    private String _buyerRatingTime;
    private String _buyerToSellerRating;
    private String _buyerToSellerReview;
    private String _category;
    private String _createdBy;
    private String _createdByAvatar;
    private String _createdByName;
    private String _description;
    private String _lastModificationTime;
    private Double _latitude;
    private String _location;
    private Double _longtitude;
    private Double _meetingLatitude;
    private Double _meetingLongtitude;
    private String _meetingTime;
    private List<String> _originalFiles;
    private List<String> _pics;
    private Double _price;
    private Double _replyCount;
    private String _sellerRatingTime;
    private String _sellerToBuyerRating;
    private String _sellerToBuyerReview;
    private String _soldTime;
    private String _soldTo;
    private String _subcategory;
    private Double _subcategoryPosition;
    private String _thumbPic;
    private Double _thumbUpCount;
    private List<String> _thumbUpUserIds;
    private List<String> _thumbUpUserNames;
    private String _title;
    private Double _viewCount;

    @DynamoDBHashKey(attributeName = "itemId")
    @DynamoDBAttribute(attributeName = "itemId")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "buyer_rating_time")
    public String getBuyerRatingTime() {
        return _buyerRatingTime;
    }

    public void setBuyerRatingTime(final String _buyerRatingTime) {
        this._buyerRatingTime = _buyerRatingTime;
    }
    @DynamoDBAttribute(attributeName = "buyer_to_seller_rating")
    public String getBuyerToSellerRating() {
        return _buyerToSellerRating;
    }

    public void setBuyerToSellerRating(final String _buyerToSellerRating) {
        this._buyerToSellerRating = _buyerToSellerRating;
    }
    @DynamoDBAttribute(attributeName = "buyer_to_seller_review")
    public String getBuyerToSellerReview() {
        return _buyerToSellerReview;
    }

    public void setBuyerToSellerReview(final String _buyerToSellerReview) {
        this._buyerToSellerReview = _buyerToSellerReview;
    }
    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return _category;
    }

    public void setCategory(final String _category) {
        this._category = _category;
    }
    @DynamoDBIndexHashKey(attributeName = "created_by", globalSecondaryIndexName = "CREATED_BY")
    public String getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(final String _createdBy) {
        this._createdBy = _createdBy;
    }
    @DynamoDBAttribute(attributeName = "created_by_avatar")
    public String getCreatedByAvatar() {
        return _createdByAvatar;
    }

    public void setCreatedByAvatar(final String _createdByAvatar) {
        this._createdByAvatar = _createdByAvatar;
    }
    @DynamoDBAttribute(attributeName = "created_by_name")
    public String getCreatedByName() {
        return _createdByName;
    }

    public void setCreatedByName(final String _createdByName) {
        this._createdByName = _createdByName;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }
    @DynamoDBIndexRangeKey(attributeName = "last_modification_time", globalSecondaryIndexName = "CREATED_BY")
    public String getLastModificationTime() {
        return _lastModificationTime;
    }

    public void setLastModificationTime(final String _lastModificationTime) {
        this._lastModificationTime = _lastModificationTime;
    }
    @DynamoDBAttribute(attributeName = "latitude")
    public Double getLatitude() {
        return _latitude;
    }

    public void setLatitude(final Double _latitude) {
        this._latitude = _latitude;
    }
    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return _location;
    }

    public void setLocation(final String _location) {
        this._location = _location;
    }
    @DynamoDBAttribute(attributeName = "longtitude")
    public Double getLongtitude() {
        return _longtitude;
    }

    public void setLongtitude(final Double _longtitude) {
        this._longtitude = _longtitude;
    }
    @DynamoDBAttribute(attributeName = "meeting_latitude")
    public Double getMeetingLatitude() {
        return _meetingLatitude;
    }

    public void setMeetingLatitude(final Double _meetingLatitude) {
        this._meetingLatitude = _meetingLatitude;
    }
    @DynamoDBAttribute(attributeName = "meeting_longtitude")
    public Double getMeetingLongtitude() {
        return _meetingLongtitude;
    }

    public void setMeetingLongtitude(final Double _meetingLongtitude) {
        this._meetingLongtitude = _meetingLongtitude;
    }
    @DynamoDBAttribute(attributeName = "meeting_time")
    public String getMeetingTime() {
        return _meetingTime;
    }

    public void setMeetingTime(final String _meetingTime) {
        this._meetingTime = _meetingTime;
    }
    @DynamoDBAttribute(attributeName = "original_files")
    public List<String> getOriginalFiles() {
        return _originalFiles;
    }

    public void setOriginalFiles(final List<String> _originalFiles) {
        this._originalFiles = _originalFiles;
    }
    @DynamoDBAttribute(attributeName = "pics")
    public List<String> getPics() {
        return _pics;
    }

    public void setPics(final List<String> _pics) {
        this._pics = _pics;
    }
    @DynamoDBAttribute(attributeName = "price")
    public Double getPrice() {
        return _price;
    }

    public void setPrice(final Double _price) {
        this._price = _price;
    }
    @DynamoDBAttribute(attributeName = "reply_count")
    public Double getReplyCount() {
        return _replyCount;
    }

    public void setReplyCount(final Double _replyCount) {
        this._replyCount = _replyCount;
    }
    @DynamoDBAttribute(attributeName = "seller_rating_time")
    public String getSellerRatingTime() {
        return _sellerRatingTime;
    }

    public void setSellerRatingTime(final String _sellerRatingTime) {
        this._sellerRatingTime = _sellerRatingTime;
    }
    @DynamoDBAttribute(attributeName = "seller_to_buyer_rating")
    public String getSellerToBuyerRating() {
        return _sellerToBuyerRating;
    }

    public void setSellerToBuyerRating(final String _sellerToBuyerRating) {
        this._sellerToBuyerRating = _sellerToBuyerRating;
    }
    @DynamoDBAttribute(attributeName = "seller_to_buyer_review")
    public String getSellerToBuyerReview() {
        return _sellerToBuyerReview;
    }

    public void setSellerToBuyerReview(final String _sellerToBuyerReview) {
        this._sellerToBuyerReview = _sellerToBuyerReview;
    }
    @DynamoDBIndexRangeKey(attributeName = "sold_time", globalSecondaryIndexName = "SOLD_TO")
    public String getSoldTime() {
        return _soldTime;
    }

    public void setSoldTime(final String _soldTime) {
        this._soldTime = _soldTime;
    }
    @DynamoDBIndexHashKey(attributeName = "sold_to", globalSecondaryIndexName = "SOLD_TO")
    public String getSoldTo() {
        return _soldTo;
    }

    public void setSoldTo(final String _soldTo) {
        this._soldTo = _soldTo;
    }
    @DynamoDBAttribute(attributeName = "subcategory")
    public String getSubcategory() {
        return _subcategory;
    }

    public void setSubcategory(final String _subcategory) {
        this._subcategory = _subcategory;
    }
    @DynamoDBAttribute(attributeName = "subcategory_position")
    public Double getSubcategoryPosition() {
        return _subcategoryPosition;
    }

    public void setSubcategoryPosition(final Double _subcategoryPosition) {
        this._subcategoryPosition = _subcategoryPosition;
    }
    @DynamoDBAttribute(attributeName = "thumb_pic")
    public String getThumbPic() {
        return _thumbPic;
    }

    public void setThumbPic(final String _thumbPic) {
        this._thumbPic = _thumbPic;
    }
    @DynamoDBAttribute(attributeName = "thumb_up_count")
    public Double getThumbUpCount() {
        return _thumbUpCount;
    }

    public void setThumbUpCount(final Double _thumbUpCount) {
        this._thumbUpCount = _thumbUpCount;
    }
    @DynamoDBAttribute(attributeName = "thumb_up_user_ids")
    public List<String> getThumbUpUserIds() {
        return _thumbUpUserIds;
    }

    public void setThumbUpUserIds(final List<String> _thumbUpUserIds) {
        this._thumbUpUserIds = _thumbUpUserIds;
    }
    @DynamoDBAttribute(attributeName = "thumb_up_user_names")
    public List<String> getThumbUpUserNames() {
        return _thumbUpUserNames;
    }

    public void setThumbUpUserNames(final List<String> _thumbUpUserNames) {
        this._thumbUpUserNames = _thumbUpUserNames;
    }
    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return _title;
    }

    public void setTitle(final String _title) {
        this._title = _title;
    }
    @DynamoDBAttribute(attributeName = "view_count")
    public Double getViewCount() {
        return _viewCount;
    }

    public void setViewCount(final Double _viewCount) {
        this._viewCount = _viewCount;
    }
}