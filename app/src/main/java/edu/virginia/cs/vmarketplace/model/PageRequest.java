package edu.virginia.cs.vmarketplace.model;

import java.util.Map;

/**
 * Created by cutehuazai on 12/20/17.
 */

public class PageRequest {
    private int pageSize;
    private int page;
    private Object token;
    private String param;

    public PageRequest(int page){
        this(10, page);
    }

    public PageRequest(int pageSize, int page){
        this(pageSize, page, null);
    }

    public PageRequest(int pageSize, int page, String param){
        this.pageSize = pageSize;
        this.page = page;
        this.param = param;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getToken() {
        return token;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public String getParam() {
        return param;
    }
}
