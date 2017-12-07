package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class ProductItemDao {
    private DynamoDBMapper mapper;
    public ProductItemDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public void insertOrUpdate(ProductItemsDO itemsDO){
        mapper.save(itemsDO);
    }

    public List<ProductItemsDO> findTop100HotPostsInOneWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        Date dateBefore7Days = cal.getTime();
        String dateQuery = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateBefore7Days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(dateQuery));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("modification_time > :val1")
                .withExpressionAttributeValues(eav);

        List<ProductItemsDO> scanResult = mapper.scan(ProductItemsDO.class, scanExpression);
        return scanResult;
    }

    public List<ProductItemsDO> findTop100NewPostsInOneWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -7);
        Date dateBefore7Days = cal.getTime();
        String dateQuery = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateBefore7Days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(dateQuery));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("modification_time > :val1")
                .withExpressionAttributeValues(eav);

        List<ProductItemsDO> scanResult = mapper.scan(ProductItemsDO.class, scanExpression);
        return scanResult;
    }

    public List<ProductItemsDO> findItemByUserId(String userId){
        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setCreatedBy(AppContextManager.getContextManager().getAppContext().getUser().getUserId());

        DynamoDBQueryExpression<ProductItemsDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                .withIndexName("CREATED_BY")
                .withHashKeyValues(itemsDO)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        return mapper.query(ProductItemsDO.class,dynamoDBQueryExpression);
    }
}
