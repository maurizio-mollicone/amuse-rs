package it.mollik.amuse.amusers.model.request;

import java.util.List;

import it.mollik.amuse.amusers.model.Page;
import it.mollik.amuse.amusers.model.RequestKey;

public class AmuseRequest<T> {
    
    private RequestKey requestKey;

    private Page page;

    private List<T> data;
    
    public AmuseRequest() {
        this.requestKey = new RequestKey("testuser");
    }

    public AmuseRequest(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    public AmuseRequest(RequestKey requestKey, List<T> data) {
        this(requestKey);
        this.data = data;
    }

    public AmuseRequest(RequestKey requestKey, Page page, List<T> data) {
        this(requestKey, data);
        this.page = page;
    }

    public RequestKey getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    
    /**
     * @return Page return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Page page) {
        this.page = page;
    }

    
    /**
     * @return List<IAmuseEntity> return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AmuseRequest [requestKey=" + requestKey + "page=" + page + ", data=" + data + "]";
    }



}
