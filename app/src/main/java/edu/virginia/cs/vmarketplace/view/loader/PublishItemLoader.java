package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.model.AppContextManager;
import edu.virginia.cs.vmarketplace.model.PublishItem;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;
import edu.virginia.cs.vmarketplace.util.AWSClientFactory;

/**
 * Created by cutehuazai on 11/29/17.
 */

public class PublishItemLoader extends AsyncTaskLoader<List<PublishItem>> {
    private DynamoDBMapper mapper;
    public PublishItemLoader(Context context) {
        super(context);
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    @Override
    public List<PublishItem> loadInBackground() {
        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setUserId(AppContextManager.getContextManager().getAppContext().getUser().getUserId());

        DynamoDBQueryExpression<ProductItemsDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                .withIndexName("SORT_BY_TIME")
                .withHashKeyValues(itemsDO)
                .withScanIndexForward(false)
                .withConsistentRead(false);

        List<ProductItemsDO> productMeta = mapper.query(ProductItemsDO.class,dynamoDBQueryExpression);

        List<PublishItem> list = new ArrayList<PublishItem>();

        for(ProductItemsDO itemDo : productMeta){
            PublishItem item = new PublishItem(itemDo);
            list.add(item);
        }

        return list;
    }
}
