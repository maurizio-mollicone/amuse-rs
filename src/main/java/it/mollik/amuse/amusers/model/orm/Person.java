package it.mollik.amuse.amusers.model.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;

import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.EEntityStatus;

@MappedSuperclass
@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person extends AmuseEntity {
    
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @AccessType(Type.PROPERTY)
    private Long id;

    @Column(name = "first_name", length = 500, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 500, nullable = false)
    private String lastName;

    @Column(name = "name", length = 500, nullable = false)
    private String name;
    
    @Lob
    @Column(name = "biography", length=100000)
    private byte[] biography;

    @Column(name = "birth_dt", length = 500)
    private Date birthDate;
    
    @Column(name = "death_dt", length = 500)
    private Date deathDate;

    @Column(name="create_ts", nullable = false)
    private Date createTs;

    @Column(name="update_ts", nullable = false)
    private Date updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EEntityStatus status;


    public Person() {
    }

    public Person(String firstName, String lastName, String name, Date createTs, Date updateTs, EEntityStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.status = status;
    }

    public Person(String firstName, String lastName, Date createTs, Date updateTs, EEntityStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + lastName;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.status = status;
    }

    public Person(String name, Date createTs, Date updateTs, EEntityStatus status) {
        this.name = name;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.status = status;
    }

    /**
     * @return Long the id
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
     * @return String the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return String the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return byte[] the biography
     */
    public byte[] getBiography() {
        return biography;
    }

    /**
     * @param biography the biography to set
     */
    public void setBiography(byte[] biography) {
        this.biography = biography;
    }

    /**
     * @return Date the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return Date the deathDate
     */
    public Date getDeathDate() {
        return deathDate;
    }

    /**
     * @param deathDate the deathDate to set
     */
    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    /**
     * @return Date the createTs
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
     * @return Date the updateTs
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
     * @return EEntityStatus the status
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
