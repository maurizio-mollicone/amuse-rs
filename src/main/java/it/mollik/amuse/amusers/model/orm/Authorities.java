package it.mollik.amuse.amusers.model.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import it.mollik.amuse.amusers.model.Role;

@Entity(name="authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 500, nullable = false)
    private String userName;

    @Column(name = "authority")
    private Role authority;    

    @Column(name="create_ts", nullable = false)
    private Date createTs = new Date();


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
     * @return Role return the authority
     */
    public Role getAuthority() {
        return authority;
    }

    /**
     * @param authority the authority to set
     */
    public void setAuthority(Role authority) {
        this.authority = authority;
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
