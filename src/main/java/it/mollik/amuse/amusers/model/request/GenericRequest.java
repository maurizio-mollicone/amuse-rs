package it.mollik.amuse.amusers.model.request;

import java.io.Serializable;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

public class GenericRequest implements Serializable {
    
    private RequestKey requestKey;

    private Page page;
    
    public GenericRequest() {
        this.requestKey = new RequestKey("testuser");
    }

    public GenericRequest(RequestKey requestKey) {
        this.requestKey = requestKey;
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

    @Override
    public String toString() {
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [ ").add(this.getRequestKey().toString()).add("]").add(", [ ").add(this.getPage().toString()).add("]").toString();
    }


}
