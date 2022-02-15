package it.mollik.amuse.amusers.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;

@Service
public interface IUserService {

    public User findById(Long id) throws EntityNotFoundException;

    public List<User> findByName(String name) throws EntityNotFoundException;
    
    public User findByUserName(String userName) throws EntityNotFoundException;

    public List<User> findByEmail(String email) throws EntityNotFoundException;

    public Boolean existsByUserName(String username);

	public Boolean existsByEmail(String email);
    
    public Page<User> list(int pageIndex, int pageSize, String sortBy) throws EntityNotFoundException;

    public User create(User user);

    public User save(User user);
    
    public User addRole(Role role) throws EntityNotFoundException;

    public User deleteRole(Role role) throws EntityNotFoundException;

    public void delete(Long userId) throws EntityNotFoundException;
}
