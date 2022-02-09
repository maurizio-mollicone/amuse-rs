package it.mollik.amuse.amusers.model.orm;

import javax.persistence.Column;
import javax.persistence.Entity;

import it.mollik.amuse.amusers.model.UserStatus;

@Entity(name = "user")
public class User extends Person {
    
    @Column(name = "user_name", length = 500, nullable = false, unique = true)
    private String userName;
    
    @Column(name = "email", length = 500, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "user_status")
    private UserStatus userStatus;    

    
    public String getUserName() {
        return userName;
    }

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
     * @return UserStatus return the userStatus
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
