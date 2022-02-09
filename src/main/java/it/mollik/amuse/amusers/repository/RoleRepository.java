package it.mollik.amuse.amusers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mollik.amuse.amusers.model.orm.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByUserName(String name);

}
