package it.mollik.amuse.amusers.model.response;

import java.util.List;

import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.RequestKey;

public class AmuseResponse<T> {
    
    private RequestKey requestKey;

    private SearchParams searchParams;
    
    private Integer statusCode;

    private String statusMessage;

    private List<T> data;

    
    public AmuseResponse() {
        this.requestKey = new RequestKey("testuser");
        this.statusCode = 0;
        this.statusMessage = "OK";
    }

    public AmuseResponse(String userName) {
        this (new RequestKey(userName), 0, "OK");
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage) {
        this.requestKey = requestKey;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage, List<T> data) {
        this(requestKey, statusCode, statusMessage);
        this.data = data;
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage, SearchParams page, List<T> data) {
        this(requestKey, statusCode, statusMessage, data);
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
     * @return Integer return the statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return String return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage the statusMessage to set
     */
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
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
        return "AmuseResponse [requestKey=" + requestKey + ", page=" + searchParams + ", statusCode=" + statusCode.intValue() + ", statusMessage=" + statusMessage + ", data=" + data + "]";
    }
}
