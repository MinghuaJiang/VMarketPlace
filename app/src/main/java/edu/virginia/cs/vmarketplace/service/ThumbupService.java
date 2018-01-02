package edu.virginia.cs.vmarketplace.service;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.ThumbupDO;
import edu.virginia.cs.vmarketplace.service.dao.ThumbupDao;

/**
 * Created by cutehuazai on 12/27/17.
 */

public class ThumbupService {
    private ThumbupDao dao;

    public static ThumbupService thumbupService = new ThumbupService();

    public static ThumbupService getInstance() {
        return thumbupService;
    }

    private ThumbupService() {
        dao = new ThumbupDao();
    }

    public void insertOrUpdate(ThumbupDO itemsDO) {
        dao.insertOrUpdate(itemsDO);
    }

    public void delete(ThumbupDO itemsDO){
        dao.delete(itemsDO);
    }

    public ThumbupDO findThumbupByKey(String[] key){
        return dao.findThumbupByKey(key);
    }
    
    public List<ThumbupDO> findThumbupByItemId(String itemId){
        return dao.findThumbupByItemId(itemId);
    }
}
