package edu.virginia.cs.vmarketplace.service.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;

/**
 * Created by cutehuazai on 12/4/17.
 */

public class ProfileItemLoader extends AsyncTaskLoader<List<ProfileItem>> {
    private DynamoDBMapper mapper;
    public ProfileItemLoader(Context context) {
        super(context);
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    @Override
    public List<ProfileItem> loadInBackground() {
        List<ProfileItem> list = new ArrayList<>();
        list.add(new ProfileItem(R.drawable.publish_24p, ProfileItem.ProfileType.PUBLISH_BY_ME, 0));
        list.add(new ProfileItem(R.drawable.sold_24p, ProfileItem.ProfileType.SOLD_BY_ME, 0));
        list.add(new ProfileItem(R.drawable.bought_24p, ProfileItem.ProfileType.BOUGHT_BY_ME, 0));
        list.add(new ProfileItem(R.drawable.star_24p, ProfileItem.ProfileType.ADDED_TO_FAVIORITE, 0));

        ProductItemsDO itemsDO = new ProductItemsDO();
        itemsDO.setCreatedBy(AppContextManager.getContextManager().getAppContext().getUser().getUserId());
        DynamoDBQueryExpression<ProductItemsDO> dynamoDBQueryExpression = new DynamoDBQueryExpression<ProductItemsDO>()
                .withHashKeyValues(itemsDO).withLimit(200)
                .withConsistentRead(false);
        List<ProductItemsDO> productMeta = mapper.query(ProductItemsDO.class,dynamoDBQueryExpression);
        list.get(0).setCount(productMeta.size());

        return list;
    }
}
