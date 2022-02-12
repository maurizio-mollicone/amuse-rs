package it.mollik.amuse.amusers.model.request;

import javax.validation.constraints.NotEmpty;

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
public class LoginRequest extends AmuseEntity {
    
    @NotEmpty(message = "Please provide a username")
    private String userName;

    @NotEmpty(message = "Please provide a password")
    private String password;

}
