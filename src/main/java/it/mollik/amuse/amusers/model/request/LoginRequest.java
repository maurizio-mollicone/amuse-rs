package it.mollik.amuse.amusers.model.request;

import javax.validation.constraints.NotEmpty;

import it.mollik.amuse.amusers.model.AmuseEntity;


public class LoginRequest extends AmuseEntity {
    
    @NotEmpty(message = "Please provide a username")
    private String userName;

    @NotEmpty(message = "Please provide a password")
    private String password;


    public LoginRequest() {
    }

    public LoginRequest(@NotEmpty(message = "Please provide a username") String userName,
            @NotEmpty(message = "Please provide a password") String password) {
        this.userName = userName;
        this.password = password;
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
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
