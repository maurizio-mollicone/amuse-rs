package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import it.mollik.amuse.amusers.model.UserStatus;

@Entity(name = "user")
public class User extends Person {
    
    @Column(name = "user_name", length = 500, nullable = false, unique = true)
    private String userName;
    
    @Column(name = "email", length = 500, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    // @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    //     inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @OneToMany(mappedBy="user")
    private List<Authorities> authorities;
    
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

    public List<Authorities> getRoles() {
        return authorities;
    }

    public void setRoles(List<Authorities> authorities) {
        this.authorities = authorities;
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
