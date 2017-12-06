package edu.virginia.cs.vmarketplace.service;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.dao.ProductItemDao;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class ProductItemService {
    private ProductItemDao dao;

    public static ProductItemService service = new ProductItemService();

    public static ProductItemService getInstance(){
        return service;
    }

    private ProductItemService(){
        dao = new ProductItemDao();
    }

    public List<ProductItemsDO> findTop100HotPostsInOneWeek(){
        return dao.findTop100HotPostsInOneWeek();
    }

    public List<ProductItemsDO> findTop100NewPostsInOneWeek(){
        return dao.findTop100NewPostsInOneWeek();
    }

    public List<ProductItemsDO> findItemByUserId(String userId){
        return dao.findItemByUserId(userId);
    }
}
