package it.mollik.amuse.amusers.model.response;

import java.util.ArrayList;
import java.util.List;

import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.RequestKey;

public class UserResponse extends GenericResponse {
    private List<User> users;

    public UserResponse(RequestKey requestKey, Integer statusCode, String statusMessage, List<User> users) {
        super(requestKey, statusCode, statusMessage);
        this.users = users;
    }
    public UserResponse(RequestKey requestKey, Integer statusCode, String statusMessage) {
        this(requestKey, statusCode, statusMessage, new ArrayList<>());
    }
    public UserResponse(RequestKey requestKey, List<User> users) {
        this(requestKey, 0, "OK", users);
    }

    public UserResponse() {
        this(new RequestKey("testuser"), new ArrayList<>());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
