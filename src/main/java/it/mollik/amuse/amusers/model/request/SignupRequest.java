package it.mollik.amuse.amusers.model.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import it.mollik.amuse.amusers.model.AmuseEntity;


public class SignupRequest extends AmuseEntity {

    @NotEmpty(message = "Please provide a userName")
    private String userName;

    @NotEmpty(message = "Please provide a fist name")
    private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @NotEmpty(message = "Please provide an email")
    private String email;

    @NotEmpty(message = "Please provide a role")
    private List<String> role;

    @NotEmpty(message = "Please provide a password")
    private String password;

    public SignupRequest() {
    }

    public SignupRequest(@NotEmpty(message = "Please provide a userName") String userName,
            @NotEmpty(message = "Please provide an email") String email,
            @NotEmpty(message = "Please provide a first name") String firstName,
            @NotEmpty(message = "Please provide a last name") String lastName,
            @NotEmpty(message = "Please provide a role") List<String> role,
            @NotEmpty(message = "Please provide a password") String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
    }


    /**
     * @return String return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return List<String> return the role
     */
    public List<String> getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(List<String> role) {
        this.role = role;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * @return String return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
