package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.virginia.cs.vmarketplace.model.PageRequest;
import edu.virginia.cs.vmarketplace.model.PageResult;
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

    public PageResult<ProductItemsDO> findNearByActivePostWithIn90Days(PageRequest request){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -90);
        Date dateBefore7Days = cal.getTime();
        String dateQuery = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateBefore7Days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(dateQuery));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("last_modification_time > :val1")
                .withExpressionAttributeValues(eav).withLimit(request.getPageSize());

        if(request.getPage() != 0){
            scanExpression.setExclusiveStartKey((Map<String, AttributeValue>)request.getToken());
        }

        ScanResultPage<ProductItemsDO> scanResult = mapper.scanPage(
                ProductItemsDO.class, scanExpression);
        PageResult<ProductItemsDO> result = new PageResult<ProductItemsDO>(scanResult.getResults(), scanResult.getLastEvaluatedKey());
        return result;
    }

    public PageResult<ProductItemsDO> findLatestActivePostWithIn90Days(PageRequest pageRequest){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -90);
        Date dateBefore7Days = cal.getTime();
        String dateQuery = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(dateBefore7Days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(dateQuery));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("last_modification_time > :val1")
                .withExpressionAttributeValues(eav).withLimit(pageRequest.getPageSize());
        if(pageRequest.getPage() != 0){
            scanExpression.setExclusiveStartKey((Map<String, AttributeValue>)pageRequest.getToken());
        }

        ScanResultPage<ProductItemsDO> scanResult = mapper.scanPage(ProductItemsDO.class, scanExpression);
        PageResult<ProductItemsDO> result = new PageResult<>(scanResult.getResults(), scanResult.getLastEvaluatedKey());
        return result;
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

    public void delete(ProductItemsDO itemsDO){
        mapper.delete(itemsDO);
    }
}
