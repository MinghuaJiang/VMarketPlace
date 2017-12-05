package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

import static java.text.DateFormat.getDateInstance;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class HomeNewTabLoader implements ProductItemDOLoaderStrategy{

    private DynamoDBMapper mapper;

    public HomeNewTabLoader() {
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    @Override
    public List<ProductItemsDO> load() {
        System.out.println("FindTop100NewPostsInOneWeek");

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

        // Top 100 New
        return scanResult;
    }
}
