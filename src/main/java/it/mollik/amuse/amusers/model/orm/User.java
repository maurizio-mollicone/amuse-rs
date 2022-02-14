package it.mollik.amuse.amusers.model.orm;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EUserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Entity(name = "user")
public class User extends Person implements UserDetails {
    
    
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


    public User() {}
    public User(String userName, String email, String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userStatus = EUserStatus.ENABLED;
        this.setName(userName);
        this.setStatus(EEntityStatus.INSERT);
    }

    public User(String username, String email, String password, List<SimpleGrantedAuthority> collect,
			EUserStatus userStatus) {

        super();
        this.userName = username;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.setName(username);
        this.setStatus(EEntityStatus.INSERT);
	}

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getUserRole().getValue()))
                .collect(Collectors.toList());
    }

    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonProperty("userName")
    public String getUsername() {
        return this.userName;
    }

}
