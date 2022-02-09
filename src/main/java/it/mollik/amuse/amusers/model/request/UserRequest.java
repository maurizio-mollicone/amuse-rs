package it.mollik.amuse.amusers.model.request;

import java.util.ArrayList;
import java.util.List;

import it.mollik.amuse.amusers.model.orm.User;

public class UserRequest extends GenericRequest {
    
    private List<User> users;

    public UserRequest() {
        super();
        this.users = new ArrayList<>();
    }

    public UserRequest(List<User> users) {
        this.users = users;
    }

    public UserRequest(RequestKey requestKey, List<User> users) {
        super(requestKey);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
