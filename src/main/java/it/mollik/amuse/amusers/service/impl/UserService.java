package it.mollik.amuse.amusers.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import it.mollik.amuse.amusers.service.IUserService;

@Service
public class UserService implements IUserService {

    
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    @Override
    public List<User> findByName(String name) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(name);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(name);
        }
        return users;

    }

    @Override
    public User findByUserName(String userName) throws EntityNotFoundException {
        return this.userRepository.findByUsername(userName).orElseThrow(() -> new EntityNotFoundException(userName));
  
    }

    @Override
    public List<User> findByEmail(String email) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(email);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(email);
        }
        return users;    
    }

    public Boolean existsByUserName(String username) {
        Boolean userExists = this.userRepository.existsByUsername(username);
        logger.info("user {} exists {}", username, userExists.booleanValue());
        return userExists;
    }

	public Boolean existsByEmail(String email) {
        Boolean emailExists = this.userRepository.existsByEmail(email);
        logger.info("email {} exists {}", email, emailExists.booleanValue());
        return emailExists;
    }

    @Override
    public Page<User> list(int pageIndex, int pageSize, String sortBy) throws EntityNotFoundException {
        Pageable page = (sortBy != null && !sortBy.isEmpty()) ? PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()) : PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        Page<User> usersPage = this.userRepository.findAll(page);
        logger.info("retrieved {}/{} users of {}, page {}/{}, pageSize ", usersPage.getNumberOfElements(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getNumber(), usersPage.getTotalPages());
        return usersPage; 
    }

    @Override
    public User create(User user) {
        Date now = new Date();
        user.setCreateTs(now);
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("create {}", result);
        return result;
    }

    @Override
    public User save(User user) {
        user.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("save {}", result);
        return result;
    }

    @Override
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
