package it.mollik.amuse.amusers.model.request;

import javax.validation.constraints.NotEmpty;

import it.mollik.amuse.amusers.model.AmuseEntity;

public class LoginRequest extends AmuseEntity {
    
    @NotEmpty(message = "Please provide a username")
    private String userName;

    @NotEmpty(message = "Please provide a password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
