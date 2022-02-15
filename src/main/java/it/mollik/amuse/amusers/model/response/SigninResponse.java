package it.mollik.amuse.amusers.model.response;

import java.util.List;

import it.mollik.amuse.amusers.model.AmuseEntity;


public class SigninResponse extends AmuseEntity {
    
    private String token;

    private Long id;

    private String userName;

    private String email;

    private List<String> roles;



    public SigninResponse() {
    }

    public SigninResponse(String token, String userName, String email, List<String> roles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public SigninResponse(String token, Long id, String userName, String email, List<String> roles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
        this.id = id;
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
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return List<String> return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}