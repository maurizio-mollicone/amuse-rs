package it.mollik.amuse.amusers.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;

@Service
public class UserService {

    
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findById(Long id) throws EntityNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public List<User> findByName(String name) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(name);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(name);
        }
        return users;
    }

    public User findByUserName(String userName) throws EntityNotFoundException {
        return this.userRepository.findByUsername(userName).orElseThrow(() -> new EntityNotFoundException(userName));
  
    }

    public List<User> findByEmail(String email) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(email);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(email);
        }
        return users;    
    }

    public Boolean existsByUserName(String username) {
        Boolean userExists = this.userRepository.existsByUsername(username);
        logger.info("user {} exists {}", username, userExists);
        return userExists;
    }

	public Boolean existsByEmail(String email) {
        Boolean emailExists = this.userRepository.existsByEmail(email);
        logger.info("email {} exists {}", email, emailExists);
        return emailExists;
    }

    public Page<User> list(int pageIndex, int pageSize, String sortBy) {
        Pageable page = (sortBy != null && !sortBy.isEmpty()) ? PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()) : PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        Page<User> usersPage = this.userRepository.findAll(page);
        logger.info("retrieved {}/{} users of {}, page {}/{}, pageSize ", usersPage.getNumberOfElements(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getNumber(), usersPage.getTotalPages());
        return usersPage; 
    }

    public User create(User user) {
        Date now = new Date();
        user.setCreateTs(now);
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("create {}", result);
        return result;
    }

    public User save(User user) {
        user.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("save {}", result);
        return result;
    }

    public User addRole(Role role) throws EntityNotFoundException {
        
        User user = this.userRepository.findByUsername(role.getUserName()).orElseThrow(() -> new EntityNotFoundException(role.getUserName()));
        Date now = new Date();
        role.setCreateTs(now);
        role.setUser(user);
        user.addRole(role);
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("addRole {}", result);

        return result;
    }

    public User deleteRole(Role role) throws EntityNotFoundException {
        
        User user = this.userRepository.findByUsername(role.getUserName()).orElseThrow(() -> new EntityNotFoundException(role.getUserName()));
        Date now = new Date();
        user.setUpdateTs(now);
        user.removeRole(role);
        User result = this.userRepository.save(user);
        logger.info("deleteRole {}", result);
        return result;
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            Role r = this.roleRepository.getReferenceById(role.getId());
            this.roleRepository.delete(r);
        }
        this.userRepository.delete(user);
        logger.info("delete {}", user);
    }
    
    
}
