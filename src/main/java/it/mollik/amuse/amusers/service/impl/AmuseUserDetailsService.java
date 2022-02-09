package it.mollik.amuse.amusers.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.model.AmuseUserDetails;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.repository.UserRepository;

@Service
public class AmuseUserDetailsService implements UserDetailsService {
    @Autowired
	UserRepository userRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return AmuseUserDetails.build(user);
	}
}
