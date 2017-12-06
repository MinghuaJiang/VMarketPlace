package edu.virginia.cs.vmarketplace.service.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import edu.virginia.cs.vmarketplace.model.CommentsDO;

/**
 * Created by cutehuazai on 12/5/17.
 */

public class CommonAsyncTaskLoader<T, R> extends AsyncTaskLoader<R> {
    private Function<T, R> function;
    private Supplier<R> supplier;
    private T param;
    public CommonAsyncTaskLoader(Context context, Function<T, R> function,
                                 Supplier<R> supplier, T param){
        super(context);
        this.function = function;
        this.supplier = supplier;
        this.param = param;
    }

    @Override
    public R loadInBackground() {
        if(function != null){
            return function.apply(param);
        }else{
            return supplier.get();
        }
    }
}
