package it.mollik.amuse.amusers.model.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EUserStatus;

@Entity(name = "user")
public class User extends Person implements UserDetails {
    
    private static final Logger logger = LoggerFactory.getLogger(User.class);

	@Column(name = "user_name", length = 500, nullable = false, unique = true)
    private String username;
    
    @Column(name = "email", length = 500, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Role> roles;
    
    @Column(name = "user_status")
    private EUserStatus userStatus;    


    public User() {}

    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.userStatus = EUserStatus.ENABLED;
        this.setName(username);
        this.setStatus(EEntityStatus.INSERT);
    }

    public User(String username, String email, String password, List<SimpleGrantedAuthority> collect,
			EUserStatus userStatus) {

        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.setName(username);
        this.setStatus(EEntityStatus.INSERT);
	}

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public EUserStatus getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(EUserStatus userStatus) {
        this.userStatus = userStatus;
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
    public String getUsername() {
        return this.username;
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        if (this.roles != null && !this.roles.isEmpty()) {
            this.roles.remove(role);
        }
    }

    @Override
    public String toString(){
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = StringUtils.EMPTY;
        try {
            jsonString = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.debug("toJSONString error", e);
        }
        return jsonString;
    }
    
    @Override
    public String toJSONString() {
        return toString();
    } 
}
