package it.mollik.amuse.amusers.model;

import java.util.List;

public class AmuseModel<T extends IAmuseEntity> extends AmuseObject {
    
    private RequestKey requestKey;

    private SearchParams searchParams;

    private List<T> data;

    public AmuseModel() {
        this.requestKey = new RequestKey("testuser");
    }

    public AmuseModel(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    public AmuseModel(RequestKey requestKey, List<T> data) {
        this(requestKey);
        this.data = data;
    }

    public AmuseModel(RequestKey requestKey, SearchParams page, List<T> data) {
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

}
