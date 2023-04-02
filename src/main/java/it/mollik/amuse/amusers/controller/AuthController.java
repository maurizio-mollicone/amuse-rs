package it.mollik.amuse.amusers.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignoutRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.SigninResponse;
import it.mollik.amuse.amusers.model.response.SignoutResponse;
import it.mollik.amuse.amusers.repository.RoleRepository;
import it.mollik.amuse.amusers.repository.UserRepository;
import it.mollik.amuse.amusers.service.HelperService;
import it.mollik.amuse.amusers.service.JwtTokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	HelperService helperService;

	@Autowired
	JwtTokenService jwtTokenService;

	/**
	 * 
	 * @param loginRequest
	 * @param webRequest
	 */
	@PostMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AmuseResponse<SigninResponse> signin(@Valid @RequestBody AmuseRequest<LoginRequest> loginRequest, 
			WebRequest webRequest) {
		logger.info("/amuse/v1/auth/signin {}", loginRequest.getData().get(0).getUserName());
		logger.debug("POST /amuse/v1/authUser/signin request : {}", loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getData().get(0).getUserName(), loginRequest.getData().get(0).getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String clientIp = helperService.getClientIp(webRequest);
		String userAgent = helperService.getUserAgent(webRequest);
		String jwt = jwtTokenService.generateTokenV2(authentication, clientIp, userAgent);
		User userDetails = (User) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		AmuseResponse<SigninResponse> response = new AmuseResponse<>(
			new Key(loginRequest.getData().get(0).getUserName()), 
			Stream.of(
				new SigninResponse(
					jwt, 
					userDetails.getId(), 
					userDetails.getUsername(), 
					userDetails.getEmail(), 
					roles)).collect(Collectors.toList()));
		logger.info("User {} authenticated", loginRequest.getData().get(0).getUserName());
		logger.debug("POST /amuse/v1/auth/signin response {}", response);
		return response;
	}

	@PostMapping(path = "/signout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	
	/**
	 *
	 * @param signoutRequest
	 * @param request
	 */
	public AmuseResponse<SignoutResponse> signout(@Valid @RequestBody AmuseRequest<SignoutRequest> signoutRequest, HttpServletRequest request) {
		logger.info("/amuse/v1/auth/signout {}", signoutRequest.getData().get(0).getUserName());
		logger.debug("POST /amuse/v1/authUser/signout request : {}", signoutRequest);
		String token = helperService.parseJwt(request);
		if (token != null) {
			if (!jwtTokenService.isInvalid(token).booleanValue()) {
				jwtTokenService.invalidateToken(token);
			} else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalid");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User " + signoutRequest.getData().get(0).getUserName() + " unauthorized");
		}
		
		AmuseResponse<SignoutResponse> response = new AmuseResponse<>(signoutRequest.getKey(), Stream.of(new SignoutResponse(token, signoutRequest.getData().get(0).getUserName())).collect(Collectors.toList()));
		logger.info("User {} signed out", signoutRequest.getData().get(0).getUserName());
		logger.debug("POST /amuse/v1/auth/signout response {}", response);
		return response;
	}


	@PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AmuseResponse<User> signup(@Valid @RequestBody AmuseRequest<SignupRequest> signUpRequest) {
		
		logger.info("/amuse/v1/auth/signup");
		logger.debug("POST /amuse/v1/auth/signup request : {}", signUpRequest);
		if (userRepository.existsByUsername(signUpRequest.getData().get(0).getUserName()).booleanValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username" + signUpRequest.getData().get(0).getUserName() + "already taken");
		}
		if (userRepository.existsByEmail(signUpRequest.getData().get(0).getEmail()).booleanValue()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email" + signUpRequest.getData().get(0).getEmail() + "already taken");
		}
		// Create new user's account
		User user = new User(signUpRequest.getData().get(0).getUserName(), signUpRequest.getData().get(0).getEmail(), encoder.encode(signUpRequest.getData().get(0).getPassword()));
        user.setFirstName(signUpRequest.getData().get(0).getFirstName());
		user.setLastName(signUpRequest.getData().get(0).getLastName());
		user.setCreateTs(new Date());
        user.setUpdateTs(new Date());
		List<String> strRoles = signUpRequest.getData().get(0).getRole();
		List<Role> roles = new ArrayList<>();
		if (strRoles == null) {
            roles.add(new Role(user.getUsername(), ERole.USER, user));
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
                        roles.add(new Role(user.getUsername(), ERole.ADMIN, user));
					break;
				case "manager":
                    roles.add(new Role(user.getUsername(), ERole.MANAGER, user));
					break;
				default:
                    roles.add(new Role(user.getUsername(), ERole.USER, user));
					break;
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
        for (Role currentRole : roles) {
            roleRepository.save(currentRole);
        }

		AmuseResponse<User> response = new AmuseResponse<>(signUpRequest.getKey(), 0, "User registered successfully!", Stream.of(user).collect(Collectors.toList()));
		logger.info("User {} registered", signUpRequest.getData().get(0).getUserName());
		logger.debug("POST /amuse/v1/auth/signup response {}", response);

		return response;
	}
}
