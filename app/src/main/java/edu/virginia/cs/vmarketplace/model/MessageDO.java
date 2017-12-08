package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/8/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-MESSAGE")

public class MessageDO {
    private String _messageThreadId;
    private String _buyerId;
    private String _itemId;
    private String _lastUpdateMessage;
    private String _lastUpdateMessageType;
    private String _lastUpdateTime;
    private String _sellerId;

    @DynamoDBHashKey(attributeName = "messageThreadId")
    @DynamoDBAttribute(attributeName = "messageThreadId")
    public String getMessageThreadId() {
        return _messageThreadId;
    }

    public void setMessageThreadId(final String _messageThreadId) {
        this._messageThreadId = _messageThreadId;
    }
    @DynamoDBAttribute(attributeName = "buyerId")
    public String getBuyerId() {
        return _buyerId;
    }

    public void setBuyerId(final String _buyerId) {
        this._buyerId = _buyerId;
    }
    @DynamoDBIndexHashKey(attributeName = "itemId", globalSecondaryIndexName = "TIME_SORT")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "lastUpdateMessage")
    public String getLastUpdateMessage() {
        return _lastUpdateMessage;
    }

    public void setLastUpdateMessage(final String _lastUpdateMessage) {
        this._lastUpdateMessage = _lastUpdateMessage;
    }
    @DynamoDBAttribute(attributeName = "lastUpdateMessageType")
    public String getLastUpdateMessageType() {
        return _lastUpdateMessageType;
    }

    public void setLastUpdateMessageType(final String _lastUpdateMessageType) {
        this._lastUpdateMessageType = _lastUpdateMessageType;
    }
    @DynamoDBIndexRangeKey(attributeName = "lastUpdateTime", globalSecondaryIndexName = "TIME_SORT")
    public String getLastUpdateTime() {
        return _lastUpdateTime;
    }

    public void setLastUpdateTime(final String _lastUpdateTime) {
        this._lastUpdateTime = _lastUpdateTime;
    }
    @DynamoDBAttribute(attributeName = "sellerId")
    public String getSellerId() {
        return _sellerId;
    }

    public void setSellerId(final String _sellerId) {
        this._sellerId = _sellerId;
    }

}
