package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.EUserStatus;

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
    private List<Role> roles;
    
    @Column(name = "user_status")
    private EUserStatus userStatus;    

    public User() {
        this(StringUtils.EMPTY);
    }
    public User(String userName) {
        super(userName);
        this.userName = userName;
        this.userStatus = EUserStatus.ENABLED;
    }

    public User(String userName, String email, String password) {
        this(userName);
        this.email = email;
        this.password = password;
        this.userStatus = EUserStatus.ENABLED;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return UserStatus return the userStatus
     */
    public EUserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(EUserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
