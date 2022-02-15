package it.mollik.amuse.amusers.model.response;

import java.util.List;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.AmuseModel;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.SearchParams;

public class AmuseResponse<T extends IAmuseEntity>  extends AmuseModel<T> {
    
    private int statusCode;

    private String statusMessage;

    public AmuseResponse() {
    }

    public AmuseResponse(Key key, int statusCode, String statusMessage, List<T> data) {
        super(key, new SearchParams(), data);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
    
    public AmuseResponse(Key key, SearchParams searchParams, List<T> data) {
        super(key, searchParams, data);
        this.statusCode = Constants.Status.Code.STATUS_CODE_OK;
        this.statusMessage = Constants.Status.Message.STATUS_MESSAGE_OK;
    }

    public AmuseResponse(Key key, List<T> data) {
        super(key, null, data);
        this.statusCode = Constants.Status.Code.STATUS_CODE_OK;
        this.statusMessage = Constants.Status.Message.STATUS_MESSAGE_OK;
    }

    

    /**
     * @return int return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(int statusCode) {
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
