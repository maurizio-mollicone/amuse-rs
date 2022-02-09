package it.mollik.amuse.amusers.model.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.EntityStatus;

@MappedSuperclass
public class Item implements Serializable {
    
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

    @Column(name = "status", nullable = false)
    private EntityStatus status;

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
    public EntityStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(EntityStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {        
        String itemId = this.getId() != null ? this.getId().toString() : StringUtils.EMPTY;
        return new StringJoiner(StringUtils.EMPTY).add("Item [").add("itemId: ").add(itemId).add(", title: ").add(this.getTitle()).add("]").toString();
    }

}
