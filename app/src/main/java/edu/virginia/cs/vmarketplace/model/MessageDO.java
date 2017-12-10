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
    private String _buyerAvatar;
    private String _buyerId;
    private String _buyerName;
    private String _itemId;
    private String _itemOriginalPicFile;
    private String _itemThumbPic;
    private String _lastUpdateMessage;
    private String _lastUpdateMessageType;
    private String _lastUpdateTime;
    private String _sellerAvatar;
    private String _sellerId;
    private String _sellerName;

    @DynamoDBHashKey(attributeName = "messageThreadId")
    @DynamoDBAttribute(attributeName = "messageThreadId")
    public String getMessageThreadId() {
        return _messageThreadId;
    }

    public void setMessageThreadId(final String _messageThreadId) {
        this._messageThreadId = _messageThreadId;
    }
    @DynamoDBAttribute(attributeName = "buyerAvatar")
    public String getBuyerAvatar() {
        return _buyerAvatar;
    }

    public void setBuyerAvatar(final String _buyerAvatar) {
        this._buyerAvatar = _buyerAvatar;
    }
    @DynamoDBIndexHashKey(attributeName = "buyerId", globalSecondaryIndexName = "BUYER_TIME")
    @DynamoDBIndexRangeKey(attributeName = "buyerId", globalSecondaryIndexName = "CHAT")
    public String getBuyerId() {
        return _buyerId;
    }

    public void setBuyerId(final String _buyerId) {
        this._buyerId = _buyerId;
    }
    @DynamoDBAttribute(attributeName = "buyerName")
    public String getBuyerName() {
        return _buyerName;
    }

    public void setBuyerName(final String _buyerName) {
        this._buyerName = _buyerName;
    }
    @DynamoDBIndexHashKey(attributeName = "itemId", globalSecondaryIndexNames = {"CHAT","TIME_SORT",})
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "itemOriginalPicFile")
    public String getItemOriginalPicFile() {
        return _itemOriginalPicFile;
    }

    public void setItemOriginalPicFile(final String _itemOriginalPicFile) {
        this._itemOriginalPicFile = _itemOriginalPicFile;
    }
    @DynamoDBAttribute(attributeName = "itemThumbPic")
    public String getItemThumbPic() {
        return _itemThumbPic;
    }

    public void setItemThumbPic(final String _itemThumbPic) {
        this._itemThumbPic = _itemThumbPic;
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
    @DynamoDBIndexRangeKey(attributeName = "lastUpdateTime", globalSecondaryIndexNames = {"BUYER_TIME","SELLER_TIME","TIME_SORT",})
    public String getLastUpdateTime() {
        return _lastUpdateTime;
    }

    public void setLastUpdateTime(final String _lastUpdateTime) {
        this._lastUpdateTime = _lastUpdateTime;
    }
    @DynamoDBAttribute(attributeName = "sellerAvatar")
    public String getSellerAvatar() {
        return _sellerAvatar;
    }

    public void setSellerAvatar(final String _sellerAvatar) {
        this._sellerAvatar = _sellerAvatar;
    }
    @DynamoDBIndexHashKey(attributeName = "sellerId", globalSecondaryIndexName = "SELLER_TIME")
    public String getSellerId() {
        return _sellerId;
    }

    public void setSellerId(final String _sellerId) {
        this._sellerId = _sellerId;
    }
    @DynamoDBAttribute(attributeName = "sellerName")
    public String getSellerName() {
        return _sellerName;
    }

    public void setSellerName(final String _sellerName) {
        this._sellerName = _sellerName;
    }
}
