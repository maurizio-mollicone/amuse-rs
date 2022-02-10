package it.mollik.amuse.amusers.model.request;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class RequestKey implements Serializable{
    
    /**
     *
     */
    private static final String DEFAULT_IP_ADDRESS = "0.0.0.0";
    /**
     *
     */
    private UUID correlationId;
    private UUID requestId;
    private Date timestamp;
    private String country;
    private String userName;
    private String ipAddress;

    public RequestKey(String userName) {
        this(UUID.randomUUID(), UUID.randomUUID(), new Date(), "en", userName, DEFAULT_IP_ADDRESS);
    }

    

    public RequestKey(UUID correlationId, UUID requestId, Date timestamp, String country, String userName,
            String ipAddress) {
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.country = country;
        this.userName = userName;
        this.ipAddress = ipAddress;
    }
    public UUID getCorrelationId() {
        return correlationId;
    }
    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }
    public UUID getRequestId() {
        return requestId;
    }
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    @Override
    public String toString() {
        
        return new StringJoiner(StringUtils.EMPTY).add(this.getClass().getName()).add(" [ correlationId: ").add(getCorrelationId().toString())
            .add(", requestId: ").add(getRequestId().toString())
            .add(", timestamp: ").add(Long.toString(getTimestamp().getTime()))
            .add(", country: ").add(getCountry())
            .add("]").toString();

    }

    /**
     * @return String return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
