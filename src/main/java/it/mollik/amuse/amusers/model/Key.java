package it.mollik.amuse.amusers.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import it.mollik.amuse.amusers.config.Constants;
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
public class Key extends AmuseObject {
    
    public Key(String userName) {
        super();
        this.requestId = UUID.randomUUID();
        this.userName = userName;
        this.timestamp = new Date();
        this.country = Locale.ITALY.getCountry();
        try {
            this.ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.ipAddress = Constants.DEFAULT_IP_ADDRESS;
        }
        

    }
    /**
     *
     */
    /**
     *
     */
    private UUID requestId;
    private Date timestamp;
    private String country;
    private String userName;
    private String ipAddress;

}
