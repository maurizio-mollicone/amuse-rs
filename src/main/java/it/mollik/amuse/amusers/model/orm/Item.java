package it.mollik.amuse.amusers.model.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.EEntityStatus;

@MappedSuperclass
public class Item extends AmuseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "year")
    private Date year;

    @Column(name="create_ts", nullable = false)
    private Date createTs;

    @Column(name="update_ts", nullable = false)
    private Date updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EEntityStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer itemId) {
        this.id = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
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

    /**
     * @return Date return the updateTs
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * @param updateTs the updateTs to set
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }


    /**
     * @return ItemStatus return the status
     */
    public EEntityStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(EEntityStatus status) {
        this.status = status;
    }

}
