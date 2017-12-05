package edu.virginia.cs.vmarketplace.view.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;
import edu.virginia.cs.vmarketplace.model.nosql.ProductItemsDO;

/**
 * Created by VINCENTWEN on 12/4/17.
 */

public class ProductItemDOLoader extends AsyncTaskLoader<List<ProductItemsDO>> {

    private ProductItemDOLoaderStrategy strategy;

    public ProductItemDOLoader(Context context,
                               ProductItemDOLoaderStrategy productItemDOLoaderStrategy) {
        super(context);
        this.strategy = productItemDOLoaderStrategy;
    }

    @Override
    public List<ProductItemsDO> loadInBackground() {
        return strategy.load();
    }
}
