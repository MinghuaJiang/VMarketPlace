package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.QueryResultPage;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.virginia.cs.vmarketplace.model.ItemStatus;
import edu.virginia.cs.vmarketplace.model.PageRequest;
import edu.virginia.cs.vmarketplace.model.PageResult;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.util.TimeUtil;

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
        Date dateBefore90days = cal.getTime();
        String dateQuery = TimeUtil.formatYYYYMMDDhhmmss(dateBefore90days);
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

    public PageResult<ProductItemsDO> getItemsByItemIds(List<String> items, PageRequest pageRequest){
        List<Object> itemsToGet = new ArrayList<>();
        for(String itemId : items){
            ProductItemsDO itemsDO = new ProductItemsDO();
            itemsDO.setItemId(itemId);
            itemsToGet.add(itemsDO);
        }
        Map<String,List<Object>> loadResultMap = mapper.batchLoad(itemsToGet);
        List<ProductItemsDO> result = loadResultMap.values().stream().flatMap(x -> x.stream()).map(x->(ProductItemsDO)x).collect(Collectors.toList());
        if(pageRequest.getPage() * pageRequest.getPageSize() + pageRequest.getPageSize() >= result.size()) {
            List<ProductItemsDO> loadResult = result.subList(pageRequest.getPage() * pageRequest.getPageSize(), result.size());
            PageResult<ProductItemsDO> finalResult =
                    new PageResult<ProductItemsDO>(result, null);
            return finalResult;
        }else{
            List<ProductItemsDO> loadResult = result.subList(pageRequest.getPage() * pageRequest.getPageSize(), pageRequest.getPage() * pageRequest.getPageSize() + pageRequest.getPageSize());
            PageResult<ProductItemsDO> finalResult =
                    new PageResult<ProductItemsDO>(result, "hasMore");
            return finalResult;
        }
    }

    public int calculateTotalPagesForLatest(int pageSize){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -90);
        Date dateBefore90days = cal.getTime();
        String dateQuery = TimeUtil.formatYYYYMMDDhhmmss(dateBefore90days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":created_by", new AttributeValue().withS(AppContextManager.getContextManager().getAppContext().getUser().getUserId()));

        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setStatus(ItemStatus.publish.toString());

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.GE)
                .withAttributeValueList(new AttributeValue().withS(dateQuery));

        DynamoDBQueryExpression<ProductItemsDO> queryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                .withIndexName("STATUS_PUBLISH").withHashKeyValues(itemsDO).
                        withScanIndexForward(false).
                        withRangeKeyCondition("last_modification_time",rangeKeyCondition).
                        withFilterExpression("created_by <> :created_by").withExpressionAttributeValues(eav).withConsistentRead(false);
        int result = mapper.count(ProductItemsDO.class, queryExpression);
        return (result - 1) / pageSize + 1;
    }

    public PageResult<ProductItemsDO> findLatestActivePostWithIn90Days(PageRequest pageRequest){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, -90);
        Date dateBefore90days = cal.getTime();
        String dateQuery = TimeUtil.formatYYYYMMDDhhmmss(dateBefore90days);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":created_by", new AttributeValue().withS(AppContextManager.getContextManager().getAppContext().getUser().getUserId()));

        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setStatus(ItemStatus.publish.toString());

        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.GE)
                .withAttributeValueList(new AttributeValue().withS(dateQuery));

        DynamoDBQueryExpression<ProductItemsDO> queryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                .withIndexName("STATUS_PUBLISH").withHashKeyValues(itemsDO).
                        withScanIndexForward(false).
                        withRangeKeyCondition("last_modification_time",rangeKeyCondition).
                        withFilterExpression("created_by <> :created_by").withExpressionAttributeValues(eav).
                        withLimit(pageRequest.getPageSize()).withConsistentRead(false);

        if(pageRequest.getPage() != 0){
            queryExpression.setExclusiveStartKey((Map<String, AttributeValue>)pageRequest.getToken());
        }

        QueryResultPage<ProductItemsDO> queryResult = mapper.queryPage(ProductItemsDO.class, queryExpression);
        List<ProductItemsDO> result = new ArrayList<>();
        result.addAll(queryResult.getResults());
        while (result.size() < pageRequest.getPageSize() && queryResult.getLastEvaluatedKey() != null){
            queryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                    .withIndexName("STATUS_PUBLISH").withHashKeyValues(itemsDO).
                            withScanIndexForward(false).
                            withRangeKeyCondition("last_modification_time",rangeKeyCondition).
                            withFilterExpression("created_by <> :created_by").withExpressionAttributeValues(eav).
                            withLimit(pageRequest.getPageSize() - result.size()).withConsistentRead(false);
            queryExpression.setExclusiveStartKey(queryResult.getLastEvaluatedKey());
            queryResult = mapper.queryPage(ProductItemsDO.class, queryExpression);
            result.addAll(queryResult.getResults());
        }

        PageResult<ProductItemsDO> finalResult = new PageResult<>(result, queryResult.getLastEvaluatedKey());
        return finalResult;
    }

    public List<ProductItemsDO> findItemByUserId(String userId){
        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setCreatedBy(userId);

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
