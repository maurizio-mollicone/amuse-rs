package it.mollik.amuse.amusers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.mollik.amuse.amusers.model.orm.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    public Optional<User>  findByUserName(String userName);

    public Optional<User> findByEmail(String email);

    public List<User> findByName(String name);
    
    public Boolean existsByUserName(String username);

	public Boolean existsByEmail(String email);
}
