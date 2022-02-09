package it.mollik.amuse.amusers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/all")
	public String home() {
		LOG.info("Spring is here");
		return "Spring is here!";
	}

	
	@GetMapping("/amuseuser")
	//@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
	@Secured(value = {"USER", "MANAGER", "ADMIN"})
	public String userAccess() {
		return "User Content.";
	}
	@GetMapping("/amusemanager")
	//@PreAuthorize("hasRole('MANAGER')")
	@Secured(value = {"MANAGER", "ADMIN"})
	public String moderatorAccess() {
		return "Moderator Board.";
	}
	@GetMapping("/amuseadmin")
//	@PreAuthorize("hasRole('ADMIN')")
	@Secured(value = {"ADMIN"})
	public String adminAccess() {
		return "Admin Board.";
	}
}
