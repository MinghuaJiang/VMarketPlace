package edu.virginia.cs.vmarketplace.service;

import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.dao.UserProfileDao;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class UserProfileService {

    private UserProfileDao dao;

    public static UserProfileService userProfileService = new UserProfileService();

    public static UserProfileService getInstance() {
        return userProfileService;
    }

    private UserProfileService() {
        dao = new UserProfileDao();
    }
 
    public void insertOrUpdate(UserProfileDO userDO){
        dao.insertOrUpdate(userDO);
    }

    public UserProfileDO findUserById(String userId){
        return dao.findUserById(userId);
    }
}
