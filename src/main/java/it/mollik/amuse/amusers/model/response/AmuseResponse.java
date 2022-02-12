package it.mollik.amuse.amusers.model.response;

import java.util.ArrayList;
import java.util.List;

import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.RequestKey;
import it.mollik.amuse.amusers.model.SearchParams;

public class AmuseResponse<T extends IAmuseEntity>  extends AmuseModel<T> {
    
    private int statusCode;

    private String statusMessage;

    
    public AmuseResponse() {
        super(new RequestKey("testuser"));
        this.statusCode = 0;
        this.statusMessage = "OK";
    }

    public AmuseResponse(String userName) {
        this(new RequestKey(userName), 0, "OK");
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage) {
        super(requestKey, new ArrayList<T>());
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage, List<T> data) {
        super(requestKey, data);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public AmuseResponse(RequestKey requestKey, Integer statusCode, String statusMessage, SearchParams page, List<T> data) {
        super(requestKey, page, data);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
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

    
}
