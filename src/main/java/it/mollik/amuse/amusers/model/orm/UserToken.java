package it.mollik.amuse.amusers.model.orm;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.AccessType.Type;
import org.springframework.data.redis.core.RedisHash;

import it.mollik.amuse.amusers.model.AmuseEntity;
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
@RedisHash("UserToken")
public class UserToken extends AmuseEntity {
    
    @Id
    @AccessType(Type.PROPERTY)
    private String token;

    private String userName;
    
    private String userAgent;
    
    private String ipAddress;
    
}
