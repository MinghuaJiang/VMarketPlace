package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.ArrayList;
import java.util.List;

import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.ProfileItem;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

/**
 * Created by cutehuazai on 1/4/18.
 */

public class ProfileItemDao {
    private DynamoDBMapper mapper;
    private AppContext appContext;
    public ProfileItemDao(){
        mapper = AWSClientFactory.getInstance().getDBMapper();
        appContext = AppContextManager.getContextManager().getAppContext();
    }

    public List<ProfileItem> getProfileItems(){
        List<ProfileItem> list = new ArrayList<>();
        list.add(new ProfileItem(R.drawable.publish_24p, ProfileItem.ProfileType.PUBLISH_BY_ME, Math.max(appContext.getUserDO().getPublishItems().size() - 1, 0)));
        list.add(new ProfileItem(R.drawable.sold_24p, ProfileItem.ProfileType.SOLD_BY_ME, Math.max(appContext.getUserDO().getSoldItems().size() - 1, 0)));
        list.add(new ProfileItem(R.drawable.bought_24p, ProfileItem.ProfileType.BOUGHT_BY_ME, Math.max(appContext.getUserDO().getBoughtItems().size() - 1, 0)));
        list.add(new ProfileItem(R.drawable.star_24p, ProfileItem.ProfileType.ADDED_TO_FAVIORITE, Math.max(appContext.getUserDO().getFavoriteItems().size() - 1, 0)));
        list.add(new ProfileItem(R.drawable.settings_24p, ProfileItem.ProfileType.SETTING, -1));
        return list;
    }
}
