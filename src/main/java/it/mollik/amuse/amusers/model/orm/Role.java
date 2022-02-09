package it.mollik.amuse.amusers.model.orm;

import java.io.Serializable;
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

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.ERole;

@Entity(name="role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 500, nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private ERole userRole;    

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    @Column(name="create_ts", nullable = false)
    private Date createTs = new Date();

    public Role() {
        this(StringUtils.EMPTY, ERole.USER, null);
    }

    public Role(String userName, ERole authority, User user) {
        super();
        this.userName = userName;
        this.userRole = authority;
        this.user = user;
    } 

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the password
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
     * @return Role return the role
     */
    public ERole getUserRole() {
        return userRole;
    }

    /**
     * @param role the authority to set
     */
    public void setUserRole(ERole role) {
        this.userRole = role;
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
