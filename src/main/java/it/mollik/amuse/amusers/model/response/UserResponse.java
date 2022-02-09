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
        super(requestKey, statusCode, statusMessage);
        this.users = new ArrayList<>();
    }
    public UserResponse(List<User> users) {
        this(new RequestKey(), 0, "OK", users);
    }

    public UserResponse() {
        this(new ArrayList<>());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
