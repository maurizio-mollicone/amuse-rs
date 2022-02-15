package it.mollik.amuse.amusers.model.request;

import it.mollik.amuse.amusers.model.AmuseEntity;

public class SignoutRequest extends AmuseEntity {
    
    private String userName;



    public SignoutRequest() {
    }

    public SignoutRequest(String userName) {
        this.userName = userName;
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
