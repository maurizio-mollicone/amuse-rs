package it.mollik.amuse.amusers.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import it.mollik.amuse.amusers.config.Constants;

public class Key extends AmuseObject {
    

    private UUID requestId;
    private Date timestamp;
    private String country;
    private String userName;
    private String ipAddress;

    public Key() {
    }

    public Key(String userName) {
        super();
        this.requestId = UUID.randomUUID();
        this.userName = userName;
        this.timestamp = new Date();
        this.country = Locale.ITALY.getCountry();
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.ipAddress = Constants.DEFAULT_IP_ADDRESS;
        }
        

    }

    /**
     * @return UUID return the requestId
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    /**
     * @return Date return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return String return the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
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

    /**
     * @return String return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
