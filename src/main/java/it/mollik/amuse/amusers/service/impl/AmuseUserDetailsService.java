package it.mollik.amuse.amusers.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.model.AmuseUserDetails;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;

@Service
public class AmuseUserDetailsService implements UserDetailsService {

    @Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		List<Role> roles = roleRepository.findByUserName(username);
		if (roles == null || roles.isEmpty()) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		user.setRoles(roles);
		return AmuseUserDetails.build(user);
	}
}
