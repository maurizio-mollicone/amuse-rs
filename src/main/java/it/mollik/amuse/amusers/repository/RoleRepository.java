package it.mollik.amuse.amusers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mollik.amuse.amusers.model.orm.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByUserName(String name);
	
}
