package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.MessageDO;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

/**
 * Created by cutehuazai on 12/9/17.
 */

public class MessageDao {
    private DynamoDBMapper mapper;
    public MessageDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public void insertOrUpdate(MessageDO messageDO){
        mapper.save(messageDO);
    }

    public void delete(MessageDO messageDao){
        mapper.delete(messageDao);
    }

    public List<MessageDO> getMessagesAsBuyer(String userId){
        MessageDO messageDO = new MessageDO();
        messageDO.setBuyerId(userId);

        DynamoDBQueryExpression<MessageDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<MessageDO>()
                .withIndexName("BUYER_TIME")
                .withHashKeyValues(messageDO)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        return mapper.query(MessageDO.class,dynamoDBQueryExpression);
    }

    public List<MessageDO> getMessagesAsSeller(String userId){
        MessageDO messageDO = new MessageDO();
        messageDO.setSellerId(userId);

        DynamoDBQueryExpression<MessageDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<MessageDO>()
                .withIndexName("SELLER_TIME")
                .withHashKeyValues(messageDO)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        return mapper.query(MessageDO.class,dynamoDBQueryExpression);
    }

    public List<MessageDO> getMessageThreadByItemIdAndBuyerId(String itemId, String buyerId){
        MessageDO messageDO = new MessageDO();
        messageDO.setItemId(itemId);

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(buyerId));

        DynamoDBQueryExpression<MessageDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<MessageDO>()
                .withIndexName("CHAT")
                .withHashKeyValues(messageDO)
                .withRangeKeyCondition("buyerId", rangeKeyCondition)
                .withConsistentRead(false);

        return mapper.query(MessageDO.class,dynamoDBQueryExpression);
    }
}
