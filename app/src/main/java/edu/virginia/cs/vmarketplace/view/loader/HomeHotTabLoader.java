package edu.virginia.cs.vmarketplace.view.loader;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

/**
 * Created by VINCENTWEN on 12/5/17.
 */

public class HomeHotTabLoader implements ProductItemDOLoaderStrategy {

    private DynamoDBMapper mapper;

    public HomeHotTabLoader() {
        this.mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    @Override
    public List<ProductItemsDO> load() {
        System.out.println("FindTop100HotPostsInOneWeek");

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

        System.out.println("scanResult size: " + scanResult.size());
        // Top 100 Hot
        return scanResult;
    }
}
