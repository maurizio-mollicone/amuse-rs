package it.mollik.amuse.amusers.model.request;

import java.util.List;

import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.RequestKey;

public class AmuseRequest<T> {
    
    private RequestKey requestKey;

    private SearchParams searchParams;

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

    public AmuseRequest(RequestKey requestKey, SearchParams page, List<T> data) {
        this(requestKey, data);
        this.searchParams = page;
    }

    public RequestKey getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    
    /**
     * @return SearchParams return the searchParams
     */
    public SearchParams getSearchParams() {
        return searchParams;
    }

    /**
     * @param searchParams the searchParams to set
     */
    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
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
        return "AmuseRequest [requestKey=" + requestKey + "page=" + searchParams + ", data=" + data + "]";
    }



}
