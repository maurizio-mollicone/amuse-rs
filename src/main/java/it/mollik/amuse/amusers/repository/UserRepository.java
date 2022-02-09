package it.mollik.amuse.amusers.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.mollik.amuse.amusers.model.orm.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    public List<User> findByUserName(String userName);

    public List<User> findByEmail(String email);

    public List<User> findByName(String name);
    
}
