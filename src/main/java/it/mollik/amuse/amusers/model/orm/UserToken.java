package it.mollik.amuse.amusers.model.orm;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import it.mollik.amuse.amusers.model.AmuseEntity;

@RedisHash("UserToken")
public class UserToken extends AmuseEntity {
    
    @Id
    @AccessType(Type.PROPERTY)
    private String token;

    private String userName;
    
    private String userAgent;
    
    private String ipAddress;
    

    public UserToken() {
    }

    public UserToken(String token, String userName, String userAgent, String ipAddress) {
        this.token = token;
        this.userName = userName;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
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
     * @return String return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
