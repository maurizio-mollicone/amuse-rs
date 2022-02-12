package it.mollik.amuse.amusers.model.response;

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
public class SignoutResponse extends AmuseEntity {
    
    private String token;

    private String type = "Bearer";

}