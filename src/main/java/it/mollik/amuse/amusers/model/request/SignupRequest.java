package it.mollik.amuse.amusers.model.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

public class SignupRequest extends GenericRequest {

    @NotEmpty(message = "Please provide a userName")
    private String userName;

    @NotEmpty(message = "Please provide an email")
    private String email;

    @NotEmpty(message = "Please provide a role")
    private Set<String> role;

    @NotEmpty(message = "Please provide a password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
