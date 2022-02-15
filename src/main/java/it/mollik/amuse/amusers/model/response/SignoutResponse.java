package it.mollik.amuse.amusers.model.response;

import it.mollik.amuse.amusers.model.AmuseEntity;


public class SignoutResponse extends AmuseEntity {
    
    private String token;

    private String type = "Bearer";

    public SignoutResponse() {
    }

    public SignoutResponse(String token, String type) {
        this.token = token;
        this.type = type;
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
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}