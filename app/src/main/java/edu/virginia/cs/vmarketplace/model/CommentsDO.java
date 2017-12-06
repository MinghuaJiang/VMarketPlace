package edu.virginia.cs.vmarketplace.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/5/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-COMMENTS")
public class CommentsDO {
    private String _commentId;
    private String _comment;
    private String _commentBy;
    private String _commentTime;
    private String _itemId;
    private String _respondId;

    @DynamoDBHashKey(attributeName = "commentId")
    @DynamoDBAttribute(attributeName = "commentId")
    public String getCommentId() {
        return _commentId;
    }

    public void setCommentId(final String _commentId) {
        this._commentId = _commentId;
    }
    @DynamoDBAttribute(attributeName = "Comment")
    public String getComment() {
        return _comment;
    }

    public void setComment(final String _comment) {
        this._comment = _comment;
    }
    @DynamoDBAttribute(attributeName = "CommentBy")
    public String getCommentBy() {
        return _commentBy;
    }

    public void setCommentBy(final String _commentBy) {
        this._commentBy = _commentBy;
    }
    @DynamoDBIndexRangeKey(attributeName = "CommentTime", globalSecondaryIndexName = "TIME")
    public String getCommentTime() {
        return _commentTime;
    }

    public void setCommentTime(final String _commentTime) {
        this._commentTime = _commentTime;
    }
    @DynamoDBIndexHashKey(attributeName = "ItemId", globalSecondaryIndexName = "TIME")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "RespondId")
    public String getRespondId() {
        return _respondId;
    }

    public void setRespondId(final String _respondId) {
        this._respondId = _respondId;
    }
}
