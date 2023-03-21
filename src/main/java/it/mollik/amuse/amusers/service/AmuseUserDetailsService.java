package it.mollik.amuse.amusers.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;

@Service
public class AmuseUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(AmuseUserDetailsService.class);

    @Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		List<Role> roles = roleRepository.findByUserName(username);
		if (roles == null || roles.isEmpty()) {
			logger.error("User Not Found with username: {}", username);
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		user.setRoles(roles);
		return user;
	}
}
