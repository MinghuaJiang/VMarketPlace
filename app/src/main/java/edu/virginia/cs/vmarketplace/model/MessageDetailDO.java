package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/8/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-MESSAGE_DETAIL")

public class MessageDetailDO {
    private String _messageId;
    private String _messageFromId;
    private String _messagePicUrl;
    private String _messageThreadId;
    private String _messageTime;
    private String _messageToUserId;
    private String _messageType;
    private String _messsageContent;

    @DynamoDBHashKey(attributeName = "messageId")
    @DynamoDBAttribute(attributeName = "messageId")
    public String getMessageId() {
        return _messageId;
    }

    public void setMessageId(final String _messageId) {
        this._messageId = _messageId;
    }
    @DynamoDBAttribute(attributeName = "messageFromId")
    public String getMessageFromId() {
        return _messageFromId;
    }

    public void setMessageFromId(final String _messageFromId) {
        this._messageFromId = _messageFromId;
    }
    @DynamoDBAttribute(attributeName = "messagePicUrl")
    public String getMessagePicUrl() {
        return _messagePicUrl;
    }

    public void setMessagePicUrl(final String _messagePicUrl) {
        this._messagePicUrl = _messagePicUrl;
    }
    @DynamoDBIndexHashKey(attributeName = "messageThreadId", globalSecondaryIndexName = "SORT_MESSAGE")
    public String getMessageThreadId() {
        return _messageThreadId;
    }

    public void setMessageThreadId(final String _messageThreadId) {
        this._messageThreadId = _messageThreadId;
    }
    @DynamoDBIndexRangeKey(attributeName = "messageTime", globalSecondaryIndexName = "SORT_MESSAGE")
    public String getMessageTime() {
        return _messageTime;
    }

    public void setMessageTime(final String _messageTime) {
        this._messageTime = _messageTime;
    }
    @DynamoDBAttribute(attributeName = "messageToUserId")
    public String getMessageToUserId() {
        return _messageToUserId;
    }

    public void setMessageToUserId(final String _messageToUserId) {
        this._messageToUserId = _messageToUserId;
    }
    @DynamoDBAttribute(attributeName = "messageType")
    public String getMessageType() {
        return _messageType;
    }

    public void setMessageType(final String _messageType) {
        this._messageType = _messageType;
    }
    @DynamoDBAttribute(attributeName = "messsageContent")
    public String getMesssageContent() {
        return _messsageContent;
    }

    public void setMesssageContent(final String _messsageContent) {
        this._messsageContent = _messsageContent;
    }
}
