package it.mollik.amuse.amusers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mollik.amuse.amusers.model.AmuseUserDetails;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.JwtResponse;
import it.mollik.amuse.amusers.model.response.MessageResponse;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;
import it.mollik.amuse.amusers.util.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		AmuseUserDetails userDetails = (AmuseUserDetails) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        user.setCreateTs(new Date());
        user.setUpdateTs(new Date());
		Set<String> strRoles = signUpRequest.getRole();
		List<Role> roles = new ArrayList<>();
		if (strRoles == null) {
            roles.add(new Role(user.getUserName(), ERole.USER, user));
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
                        roles.add(new Role(user.getUserName(), ERole.ADMIN, user));
					break;
				case "manager":
                    roles.add(new Role(user.getUserName(), ERole.MANAGER, user));
					break;
				default:
                    roles.add(new Role(user.getUserName(), ERole.USER, user));
					break;
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
        for (Role currentRole : roles) {
            roleRepository.save(currentRole);
        }
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
