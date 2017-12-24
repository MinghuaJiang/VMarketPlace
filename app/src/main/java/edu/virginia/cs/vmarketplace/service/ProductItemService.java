package edu.virginia.cs.vmarketplace.service;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.PageRequest;
import edu.virginia.cs.vmarketplace.model.PageResult;
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

    public void save(ProductItemsDO itemsDO){
        dao.insertOrUpdate(itemsDO);
    }

    public PageResult<ProductItemsDO> findNearByActivePostWithIn90Days(PageRequest request){
        return dao.findNearByActivePostWithIn90Days(request);
    }

    public PageResult<ProductItemsDO> findLatestActivePostWithIn90Days(PageRequest request){
        return dao.findLatestActivePostWithIn90Days(request);
    }

    public List<ProductItemsDO> findItemByUserId(String userId){
        return dao.findItemByUserId(userId);
    }

    public void delete(ProductItemsDO itemsDO){
        dao.delete(itemsDO);
    }
}
