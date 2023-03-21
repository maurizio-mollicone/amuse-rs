package it.mollik.amuse.amusers.model.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.ERole;

@Entity(name="role")
public class Role extends AmuseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 500, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private ERole userRole;    

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    @Column(name="create_ts", nullable = false)
    private Date createTs = new Date();

    public Role() {
    }

    public Role(String userName, ERole role, User user) {
        super();
        this.userName = userName;
        this.userRole = role;
        this.user = user;
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
     * @return ERole return the userRole
     */
    public ERole getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(ERole userRole) {
        this.userRole = userRole;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Date return the createTs
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * @param createTs the createTs to set
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

}
