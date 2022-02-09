package it.mollik.amuse.amusers.model.orm;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.apache.commons.lang3.StringUtils;

import it.mollik.amuse.amusers.model.EEntityStatus;

@MappedSuperclass
@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Artist implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "bio", length = 1000)
    private String bio;

    @Column(name = "country", nullable = false)
    private Locale country;

    @Column(name="create_ts", nullable = false)
    private Date createTs;

    @Column(name="update_ts", nullable = false)
    private Date updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EEntityStatus status;

    public Artist() {
        this(StringUtils.EMPTY, Locale.ITALY, EEntityStatus.INSERT);        
    }

    public Artist(String name, Locale country) {
        this(name, country, EEntityStatus.INSERT);
    }

    public Artist(String name, Locale country, EEntityStatus status) {
        this.name = name;
        this.country = country;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Locale getCountry() {
        return country;
    }

    public void setCountry(Locale country) {
        this.country = country;
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
     * @return EntityStatus return the status
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

    @Override
    public String toString() {
        String artistId = getId() != null ? this.getId().toString() : StringUtils.EMPTY;
        return new StringJoiner(StringUtils.EMPTY).add("Artist: [").add("artistId: ").add(artistId).add(", name: ").add(this.getName()).add("]").toString();
    }

}
