package it.mollik.amuse.amusers.model.request;

import java.io.Serializable;
import java.util.StringJoiner;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;

public class GenericRequest implements Serializable {
    
    @NotEmpty
    private RequestKey requestKey;

    private int pageOffset;

    private int pageSize;

    public GenericRequest() {
        this.requestKey = new RequestKey();
    }

    public GenericRequest(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public RequestKey getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(RequestKey requestKey) {
        this.requestKey = requestKey;
    }

    @Override
    public String toString() {
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [ ").add(this.getRequestKey().toString()).add("]").toString();
    }
}
