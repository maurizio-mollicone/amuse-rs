package it.mollik.amuse.amusers.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EntityStatus;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.repository.UserRepository;
import it.mollik.amuse.amusers.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) throws EntityNotFoundException {
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
    public List<User> findByUserName(String userName) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(userName);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(userName);
        }
        return users;    
    }

    @Override
    public List<User> findByEmail(String email) throws EntityNotFoundException {
        List<User> users = this.userRepository.findByName(email);

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException(email);
        }
        return users;    
    }

    @Override
    public List<User> list() throws EntityNotFoundException {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(users::add);

        return users; 
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
        user.setStatus(EntityStatus.UPDATE);
        Date now = new Date();
        user.setUpdateTs(now);
        User result = this.userRepository.save(user);
        logger.info("save {}", result);
        return result;
    }

    @Override
    public void delete(Integer authorId) throws EntityNotFoundException {
        User user = this.userRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException(authorId.toString()));
        this.userRepository.delete(user);
        logger.info("delete {}", user);
    }
    
}
