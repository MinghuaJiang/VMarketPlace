package edu.virginia.cs.vmarketplace.model.nosql;

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
    private String _userId;
    private String _itemId;
    private String _category;
    private String _description;
    private Double _latitude;
    private String _locationName;
    private Double _longtitude;
    private String _modificationTime;
    private List<String> _originalFiles;
    private List<String> _pics;
    private Double _price;
    private String _subcategory;
    private Double _subcategoryPosition;
    private String _thumbPic;
    private String _title;
    private Double _viewCount;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "SORT_BY_TIME")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "itemId")
    @DynamoDBAttribute(attributeName = "itemId")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return _category;
    }

    public void setCategory(final String _category) {
        this._category = _category;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }
    @DynamoDBAttribute(attributeName = "latitude")
    public Double getLatitude() {
        return _latitude;
    }

    public void setLatitude(final Double _latitude) {
        this._latitude = _latitude;
    }
    @DynamoDBAttribute(attributeName = "location_name")
    public String getLocationName() {
        return _locationName;
    }

    public void setLocationName(final String _locationName) {
        this._locationName = _locationName;
    }
    @DynamoDBAttribute(attributeName = "longtitude")
    public Double getLongtitude() {
        return _longtitude;
    }

    public void setLongtitude(final Double _longtitude) {
        this._longtitude = _longtitude;
    }
    @DynamoDBIndexRangeKey(attributeName = "modification_time", globalSecondaryIndexName = "SORT_BY_TIME")
    public String getModificationTime() {
        return _modificationTime;
    }

    public void setModificationTime(final String _modificationTime) {
        this._modificationTime = _modificationTime;
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
