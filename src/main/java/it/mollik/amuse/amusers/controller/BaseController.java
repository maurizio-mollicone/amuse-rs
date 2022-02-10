package it.mollik.amuse.amusers.controller;

import java.security.Principal;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/heartbeat")
	public String heartbeat() {
		LOG.info("aMuse yourself!");
		return "aMuse yourself!";
	}

	@GetMapping("/amuseuser")
	@PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public String userAccess(Authentication authentication) {
		return (new StringJoiner(";")).add(authentication.getName()).add(getUserRoles(authentication).toString()).toString();
	}

	@GetMapping("/amusemanager")
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
	public String managerAccess() {
		return "Manager Board.";
	}
	
	@GetMapping("/amuseadmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	private List<String> getUserRoles(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		List<String> role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		return role;
	}
}
