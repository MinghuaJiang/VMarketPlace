package edu.virginia.cs.vmarketplace.service.dao;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.client.AWSClientFactory;
import edu.virginia.cs.vmarketplace.service.login.AppUser;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class UserProfileDao {
    private DynamoDBMapper mapper;
    public UserProfileDao (){
        mapper = AWSClientFactory.getInstance().getDBMapper();
    }
  
    public void insertOrUpdate(UserProfileDO userDO){
        mapper.save(userDO);
    } 

    public UserProfileDO findUserById(String userId){
        return mapper.load(UserProfileDO.class, userId);
    }
}
