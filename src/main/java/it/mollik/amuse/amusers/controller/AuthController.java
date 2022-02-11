package it.mollik.amuse.amusers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import it.mollik.amuse.amusers.model.AmuseUserDetails;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.RequestKey;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.JwtResponse;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;
import it.mollik.amuse.amusers.util.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
	public AmuseResponse<JwtResponse> signin(@Valid @RequestBody AmuseRequest<LoginRequest> loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getData().get(0).getName(), loginRequest.getData().get(0).getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		AmuseUserDetails userDetails = (AmuseUserDetails) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		AmuseResponse<JwtResponse> response = new AmuseResponse<>(
			new RequestKey(loginRequest.getData().get(0).getName()), 
			0, 
			"OK",
			Stream.of(
				new JwtResponse(
					jwt, 
					userDetails.getId(), 
					userDetails.getUsername(), 
					userDetails.getEmail(), 
					roles)).collect(Collectors.toList()));
		logger.info("User {} authenticated {}", loginRequest.getData().get(0).getUserName(), response);
		return response;
	}

	@PostMapping("/signup")
	public AmuseResponse<User> signup(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUserName()).booleanValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username" + signUpRequest.getUserName() + "already taken");
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail()).booleanValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email" + signUpRequest.getEmail() + "already taken");
		}
		// Create new user's account
		User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        user.setCreateTs(new Date());
        user.setUpdateTs(new Date());
		List<String> strRoles = signUpRequest.getRole();
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

		AmuseResponse<User> response = new AmuseResponse<>(new RequestKey(user.getName()), 0, "User registered successfully!");
		response.setData(Stream.of(user).collect(Collectors.toList()));
		logger.info("User {} registrated {}", signUpRequest.getUserName(), response);
		return response;
	}
}
