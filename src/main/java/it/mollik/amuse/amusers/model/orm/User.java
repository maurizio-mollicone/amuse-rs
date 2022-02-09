package it.mollik.amuse.amusers.model.orm;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "user")
public class User extends Person {
    
    @Column(name = "email", length = 500, nullable = false)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "user_status")
    private int userStatus;    

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

    /**
     * @return int return the userStatus
     */
    public int getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

}
