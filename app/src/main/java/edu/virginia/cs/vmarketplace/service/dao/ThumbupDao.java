package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.ThumbupDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by cutehuazai on 12/27/17.
 */

public class ThumbupDao {
    private DynamoDBMapper mapper;
    public ThumbupDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public void insertOrUpdate(ThumbupDO itemsDO) {
        mapper.save(itemsDO);
    }

    public void delete(ThumbupDO itemsDO){
        mapper.delete(itemsDO);
    }

    public ThumbupDO findThumbupByKey(String[] key){
        return mapper.load(ThumbupDO.class, key[0], key[1]);
    }

    public List<ThumbupDO> findThumbupByItemId(String itemId){
        ThumbupDO itemsDO = new ThumbupDO();
        itemsDO.setItemId(itemId);

        DynamoDBQueryExpression<ThumbupDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<ThumbupDO>()
                .withIndexName("TIME")
                .withHashKeyValues(itemsDO)
                .withScanIndexForward(false)
                .withLimit(100)
                .withConsistentRead(false);
        return new ArrayList<>(mapper.query(ThumbupDO.class, dynamoDBQueryExpression));
    }
}
