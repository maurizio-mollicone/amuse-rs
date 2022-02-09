package it.mollik.amuse.amusers.model.response;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.request.GenericRequest;
import it.mollik.amuse.amusers.model.request.RequestKey;

public class GenericResponse extends GenericRequest {
    
    private Integer statusCode;

    private String statusMessage;

    public GenericResponse() {
        super(new RequestKey());
        this.statusCode = 0;
        this.statusMessage = "OK";
    }


    public GenericResponse(RequestKey requestKey, Integer statusCode, String statusMessage) {
        super(requestKey);
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

    @Override
    public String toString() {
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [ ").add(this.getRequestKey().toString()).add("]").toString();
    }

}
