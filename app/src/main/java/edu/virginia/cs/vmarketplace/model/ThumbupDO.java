package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/27/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-THUMBUP")
public class ThumbupDO {
    private String _itemId;
    private String _thumbupById;
    private String _thumbupByAvatar;
    private String _thumbupByName;
    private String _thumbupTime;

    @DynamoDBHashKey(attributeName = "itemId")
    @DynamoDBIndexHashKey(attributeName = "itemId", globalSecondaryIndexName = "TIME")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBRangeKey(attributeName = "thumbupById")
    @DynamoDBAttribute(attributeName = "thumbupById")
    public String getThumbupById() {
        return _thumbupById;
    }

    public void setThumbupById(final String _thumbupById) {
        this._thumbupById = _thumbupById;
    }
    @DynamoDBAttribute(attributeName = "thumbupByAvatar")
    public String getThumbupByAvatar() {
        return _thumbupByAvatar;
    }

    public void setThumbupByAvatar(final String _thumbupByAvatar) {
        this._thumbupByAvatar = _thumbupByAvatar;
    }
    @DynamoDBAttribute(attributeName = "thumbupByName")
    public String getThumbupByName() {
        return _thumbupByName;
    }

    public void setThumbupByName(final String _thumbupByName) {
        this._thumbupByName = _thumbupByName;
    }
    @DynamoDBIndexRangeKey(attributeName = "thumbupTime", globalSecondaryIndexName = "TIME")
    public String getThumbupTime() {
        return _thumbupTime;
    }

    public void setThumbupTime(final String _thumbupTime) {
        this._thumbupTime = _thumbupTime;
    }
}
