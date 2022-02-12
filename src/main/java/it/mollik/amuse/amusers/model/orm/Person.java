package it.mollik.amuse.amusers.model.orm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.EEntityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonIdentityInfo(generator =  ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person extends AmuseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name="create_ts", nullable = false)
    private Date createTs;

    @Column(name="update_ts", nullable = false)
    private Date updateTs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EEntityStatus status;

}
