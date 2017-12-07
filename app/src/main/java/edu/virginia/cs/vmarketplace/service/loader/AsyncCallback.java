package edu.virginia.cs.vmarketplace.service.loader;

/**
 * Created by cutehuazai on 12/7/17.
 */
@FunctionalInterface
public interface AsyncCallback<T> {
    public void runCallback(T result);
}
