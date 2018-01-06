package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppUser;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class UserProfileDao {
    private DynamoDBMapper mapper;

    public UserProfileDao() {
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }

    public void insertOrUpdate(UserProfileDO userDO) {
        mapper.save(userDO);
    }

    public void batchSave(List<UserProfileDO> userDOs){
        mapper.batchSave(userDOs);
    }

    public UserProfileDO findUserById(String userId) {
        return mapper.load(UserProfileDO.class, userId);
    }

    public List<UserProfileDO> findUsersByIds(List<String> userIds) {
        List<Object> itemsToGet = new ArrayList<>();

        for (String userId : userIds) {
            UserProfileDO userProfileDO = new UserProfileDO();
            userProfileDO.setUserId(userId);
            itemsToGet.add(userProfileDO);
        }

        Map<String, List<Object>> loadResultMap = mapper.batchLoad(itemsToGet);
        List<UserProfileDO> result = loadResultMap.values().stream().flatMap(x -> x.stream()).map(x -> (UserProfileDO) x).collect(Collectors.toList());
        return result;
    }
}
