package it.mollik.amuse.amusers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.orm.User;

@Service
public interface UserService {

    public User findById(Integer id) throws EntityNotFoundException;

    public List<User> findByName(String name) throws EntityNotFoundException;
    
    public List<User> findByUserName(String userName) throws EntityNotFoundException;

    public List<User> findByEmail(String email) throws EntityNotFoundException;

    public List<User> list() throws EntityNotFoundException;

    public User create(User user);

    public User save(User user);
    
    public void delete(Integer authorId) throws EntityNotFoundException;
}
