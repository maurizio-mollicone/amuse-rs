package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EUserStatus;
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
@Entity(name = "user")
public class User extends Person {
    
    public User(String userName, String email, String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userStatus = EUserStatus.ENABLED;
        this.setName(userName);
        this.setStatus(EEntityStatus.INSERT);
    }

    @Column(name = "user_name", length = 500, nullable = false, unique = true)
    private String userName;
    
    @Column(name = "email", length = 500, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @OneToMany(mappedBy="user")
    private List<Role> roles;
    
    @Column(name = "user_status")
    private EUserStatus userStatus;    


}
