package edu.virginia.cs.vmarketplace.model;

import java.util.List;

/**
 * Created by cutehuazai on 12/23/17.
 */

public class PageResult<T> {
    private List<T> result;
    private Object token;
    public PageResult(List<T> result, Object token){
        this.result = result;
        this.token = token;
    }

    public List<T> getResult() {
        return result;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }
}
