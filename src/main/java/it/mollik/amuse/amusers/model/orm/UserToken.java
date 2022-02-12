package it.mollik.amuse.amusers.model.orm;

import javax.persistence.Entity;
import javax.persistence.Id;

import it.mollik.amuse.amusers.model.AmuseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserToken extends AmuseEntity {
    
    @Id
    private String token;

    private String userName;
    
    private String userAgent;
    
    private String ipAddress;
    
}
