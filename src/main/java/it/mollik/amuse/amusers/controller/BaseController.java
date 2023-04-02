package it.mollik.amuse.amusers.controller;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.response.AmuseResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.Api.TEST_API)
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	AuthenticationManager authenticationManager;
	
	@GetMapping("/heartbeat")
	public @ResponseBody AmuseResponse<AmuseEntity> heartbeat() {
		logger.info("aMuse yourself!");
		return new AmuseResponse<>(new Key("testuser"), 0, "aMuse yourself!", null);	
	}

	private AmuseResponse<AmuseEntity> checkPath(Authentication authentication) {
		String msg = (new StringJoiner(";")).add(authentication.getName()).add(getUserRoles(authentication).toString()).toString();
		logger.info(msg);
		return new AmuseResponse<>(new Key(authentication.getName()), 0, msg, null);
	}

	@GetMapping("/amuseuser")
	@PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public @ResponseBody AmuseResponse<AmuseEntity> userAccess(Authentication authentication) {
		return checkPath(authentication);
	}

	@GetMapping("/amusemanager")
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public AmuseResponse<AmuseEntity> managerAccess(Authentication authentication) {
		return checkPath(authentication);
	}
	
	@GetMapping("/amuseadmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public AmuseResponse<AmuseEntity> adminAccess(Authentication authentication) {
		return checkPath(authentication);
	}

	private List<String> getUserRoles(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		 
	}
	
    public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}
