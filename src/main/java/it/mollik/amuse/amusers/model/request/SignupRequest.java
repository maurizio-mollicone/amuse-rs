package it.mollik.amuse.amusers.model.request;

import java.util.List;

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
public class SignupRequest extends AmuseEntity {

    @NotEmpty(message = "Please provide a userName")
    private String userName;

    @NotEmpty(message = "Please provide an email")
    private String email;

    @NotEmpty(message = "Please provide a role")
    private List<String> role;

    @NotEmpty(message = "Please provide a password")
    private String password;

}
