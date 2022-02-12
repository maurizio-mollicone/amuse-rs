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
@Entity(name="role")
public class Role extends AmuseEntity {

    public Role(String userName, ERole role, User user) {
        super();
        this.userName = userName;
        this.userRole = role;
        this.user = user;
    }

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


}
