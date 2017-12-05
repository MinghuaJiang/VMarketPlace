package edu.virginia.cs.vmarketplace.model.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by cutehuazai on 12/5/17.
 */

@DynamoDBTable(tableName = "vmarketplace-mobilehub-440270839-TRANSACTION")

public class TransactionDO {
    private String _itemId;
    private String _buyerComment;
    private String _buyerId;
    private String _buyerName;
    private Double _buyerRating;
    private String _sellerComment;
    private String _sellerId;
    private String _sellerName;
    private Double _sellerRating;
    private String _transactionTime;

    @DynamoDBHashKey(attributeName = "itemId")
    @DynamoDBAttribute(attributeName = "itemId")
    public String getItemId() {
        return _itemId;
    }

    public void setItemId(final String _itemId) {
        this._itemId = _itemId;
    }
    @DynamoDBAttribute(attributeName = "buyerComment")
    public String getBuyerComment() {
        return _buyerComment;
    }

    public void setBuyerComment(final String _buyerComment) {
        this._buyerComment = _buyerComment;
    }
    @DynamoDBAttribute(attributeName = "buyerId")
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
    @DynamoDBAttribute(attributeName = "buyerRating")
    public Double getBuyerRating() {
        return _buyerRating;
    }

    public void setBuyerRating(final Double _buyerRating) {
        this._buyerRating = _buyerRating;
    }
    @DynamoDBAttribute(attributeName = "sellerComment")
    public String getSellerComment() {
        return _sellerComment;
    }

    public void setSellerComment(final String _sellerComment) {
        this._sellerComment = _sellerComment;
    }
    @DynamoDBAttribute(attributeName = "sellerId")
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
    @DynamoDBAttribute(attributeName = "sellerRating")
    public Double getSellerRating() {
        return _sellerRating;
    }

    public void setSellerRating(final Double _sellerRating) {
        this._sellerRating = _sellerRating;
    }
    @DynamoDBAttribute(attributeName = "transactionTime")
    public String getTransactionTime() {
        return _transactionTime;
    }

    public void setTransactionTime(final String _transactionTime) {
        this._transactionTime = _transactionTime;
    }

}
