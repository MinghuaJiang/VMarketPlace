package edu.virginia.cs.vmarketplace.service.loader;

import android.os.AsyncTask;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by cutehuazai on 12/7/17.
 */

public class CommonAyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private Supplier<Result> supplier;
    private Consumer<Params> consumer;
    private Function<Params, Result> function;
    private Params params;
    private AsyncCallback<Result> callback;

    public CommonAyncTask(Supplier<Result> supplier){
        this.supplier = supplier;
    }

    public CommonAyncTask(Consumer<Params> consumer, Params params){
        this.consumer = consumer;
        this.params = params;
    }

    public CommonAyncTask(Function<Params, Result> function, Params params){
        this.params = params;
        this.function = function;
    }

    public CommonAyncTask with(AsyncCallback<Result> callback){
        this.callback = callback;
        return this;
    }

    @Override
    protected Result doInBackground(Params[] params) {
        if(supplier != null) {
            return supplier.get();
        }else if(consumer != null){
            consumer.accept(params[0]);
            return null;
        }else{
            return function.apply(params[0]);
        }
    }

    protected void onPostExecute(Result result){
        if(callback != null){
            callback.runCallback(result);
        }
    }

    public void run(){
        if(supplier != null){
            this.execute();
        }else{
            this.execute(params);
        }
    }
}
