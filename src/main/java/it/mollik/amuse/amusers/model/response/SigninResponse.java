package it.mollik.amuse.amusers.model.response;

import java.util.List;

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
public class SigninResponse extends AmuseEntity {
    
    private String token;

    private String type = "Bearer";

    private Long id;

    private String userName;

    private String email;

    private List<String> roles;


}