package edu.virginia.cs.vmarketplace.model;

/**
 * Created by cutehuazai on 12/20/17.
 */

public class PageRequest {
    private int pageSize;
    private int page;
    private Object token;

    public PageRequest(int page){
        this(10, page);
    }

    public PageRequest(int pageSize, int page){
        this.pageSize = pageSize;
        this.page = page;
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
}
