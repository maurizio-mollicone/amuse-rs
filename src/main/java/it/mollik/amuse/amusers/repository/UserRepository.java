package it.mollik.amuse.amusers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.mollik.amuse.amusers.model.orm.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);

    public List<User> findByName(String name);
    
    public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);
}
