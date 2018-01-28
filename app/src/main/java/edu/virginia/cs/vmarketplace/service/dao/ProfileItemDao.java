package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        list.add(new ProfileItem(R.drawable.publish_24p, ProfileItem.ProfileType.PUBLISH_BY_ME.getMessage(), Math.max(appContext.getUserDO().getPublishItems().size() - 1, 0), true));
        list.add(new ProfileItem(R.drawable.sold_24p, ProfileItem.ProfileType.SOLD_BY_ME.getMessage(), Math.max(appContext.getUserDO().getSoldItems().size() - 1, 0),true));
        list.add(new ProfileItem(R.drawable.bought_24p, ProfileItem.ProfileType.BOUGHT_BY_ME.getMessage(), Math.max(appContext.getUserDO().getBoughtItems().size() - 1, 0),true));
        list.add(new ProfileItem(R.drawable.star_24p, ProfileItem.ProfileType.ADDED_TO_FAVIORITE.getMessage(), Math.max(appContext.getUserDO().getFavoriteItems().size() - 1, 0),true));
        list.add(new ProfileItem(R.drawable.settings_24p, ProfileItem.ProfileType.SETTING.getMessage(), -1, true));
        return list;
    }

    public List<ProfileItem> getAboutItems(){
        List<ProfileItem> list = new ArrayList<>();
        list.add(new ProfileItem(-1, "Check Update", -1, true));
        list.add(new ProfileItem(-1, "License Agreement",  -1,true));
        list.add(new ProfileItem(R.drawable.gmail, "Email Me", -1,true));
        return list;
    }

    public List<ProfileItem> getSettingItems(){
        List<ProfileItem> list = new ArrayList<>();
        list.add(new ProfileItem(-1, ProfileItem.SettingType.PROFILE.getMessage(), -1, true));
        list.add(new ProfileItem(-1, ProfileItem.SettingType.SHARE.getMessage(), -1,true));
        list.add(new ProfileItem(-1, ProfileItem.SettingType.ABOUT.getMessage(), -1,true));
        list.add(new ProfileItem(-1, ProfileItem.SettingType.CACHE_CLEANUP.getMessage(), -1,false));
        return list;
    }

    public String[] getSchools(){
        String[] result = new String[]{
                "Architecture",
                "Arts & Sciences",
                "Business (Darden)",
                "Commerce (McIntire)",
                "Education (Curry)",
                "Engineering & Applied Sciences",
                "Law",
                "Leadership & Public Policy (Frank Batten)",
                "Medicine",
                "Nursing",
                "Other"
        };
        return result;
    }

    public List<String> getDepartments(String school){
        Map<String, List<String>> result = new HashMap<>();
        List<String> artScience = new ArrayList<>();
        result.put("Arts & Sciences", artScience);
        artScience.add("African-American & African Studies");
        artScience.add("Anthropology");
        artScience.add("Art");
        artScience.add("Astronomy");
        artScience.add("Biology");
        artScience.add("Chemistry");

        return result.get(school);
    }

}
