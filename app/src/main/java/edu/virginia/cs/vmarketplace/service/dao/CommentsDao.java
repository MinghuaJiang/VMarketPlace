package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.CommentsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommentsDao {
    private DynamoDBMapper mapper;

    public CommentsDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public List<CommentsDO> findCommentsByItemId(String itemId){
        CommentsDO commentsDO = new CommentsDO();
        commentsDO.setItemId(itemId);
        DynamoDBQueryExpression<CommentsDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<CommentsDO>()
                .withIndexName("TIME").withHashKeyValues(commentsDO).withScanIndexForward(false).
                        withLimit(200)
                .withConsistentRead(false);
        return mapper.query(CommentsDO.class,dynamoDBQueryExpression);
    }

    public void deleteComment(CommentsDO commentsDO){
        mapper.delete(commentsDO);
    }
}
