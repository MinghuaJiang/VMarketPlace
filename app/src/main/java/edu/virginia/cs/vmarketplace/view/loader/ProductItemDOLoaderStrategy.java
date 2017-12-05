package edu.virginia.cs.vmarketplace.view.loader;

import java.util.List;

import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;

/**
 * Created by VINCENTWEN on 12/5/17.
 */

public interface ProductItemDOLoaderStrategy {

    public List<ProductItemsDO> load();
}
