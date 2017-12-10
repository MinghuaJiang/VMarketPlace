package edu.virginia.cs.vmarketplace.service.dao;

import android.os.Message;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.MessageDetailDO;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

/**
 * Created by cutehuazai on 12/9/17.
 */

public class MessageDetailDao {
    private DynamoDBMapper mapper;
    public MessageDetailDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public void insertOrUpdate(MessageDetailDO messageDetailDO){
        mapper.save(messageDetailDO);
    }

    public void delete(MessageDetailDO messageDetailDO){
        mapper.delete(messageDetailDO);
    }

    public List<MessageDetailDO> getMessages(String messageThreadId){
        MessageDetailDO messageDetailDO = new MessageDetailDO();
        messageDetailDO.setMessageThreadId(messageThreadId);

        DynamoDBQueryExpression<MessageDetailDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<MessageDetailDO>()
                .withIndexName("SORT_MESSAGE")
                .withHashKeyValues(messageDetailDO)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        return mapper.query(MessageDetailDO.class,dynamoDBQueryExpression);
    }
}
