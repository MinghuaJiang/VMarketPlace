package edu.virginia.cs.vmarketplace.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.virginia.cs.vmarketplace.model.PageRequest;
import edu.virginia.cs.vmarketplace.model.PageResult;
import edu.virginia.cs.vmarketplace.model.ProductItemsDO;
import edu.virginia.cs.vmarketplace.service.dao.ProductItemDao;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class ProductItemService {
    private Map<String, Object> cache;

    private static final String LATEST = "latest";

    private ProductItemDao dao;

    public static ProductItemService service = new ProductItemService();

    public static ProductItemService getInstance(){
        return service;
    }

    private ProductItemService(){
        dao = new ProductItemDao();
        cache = new HashMap<String, Object>();
    }

    public void save(ProductItemsDO itemsDO){
        dao.insertOrUpdate(itemsDO);
    }

    public PageResult<ProductItemsDO> findNearByActivePostWithIn90Days(PageRequest request){
        return dao.findNearByActivePostWithIn90Days(request);
    }

    public PageResult<ProductItemsDO> findLatestActivePostWithIn90Days(PageRequest request){
        if(!cache.containsKey(LATEST)){
            cache.put(LATEST, dao.calculateTotalPagesForLatest(request.getPageSize()));
        }
        int totalPage = (int)cache.get(LATEST);
        PageResult<ProductItemsDO> result = dao.findLatestActivePostWithIn90Days(request);
        if(request.getPage() == totalPage - 1){
            result.setToken(null);
        }
        return result;
    }

    public PageResult<ProductItemsDO> getItemsByItemIds(List<String> items, PageRequest pageRequest){
        return dao.getItemsByItemIds(items, pageRequest);
    }

    public void clearCache(){
        cache.clear();
    }

    public List<ProductItemsDO> findItemByUserId(String userId){
        return dao.findItemByUserId(userId);
    }

    public ProductItemsDO delete(ProductItemsDO itemsDO){
        return dao.delete(itemsDO);
    }
}
