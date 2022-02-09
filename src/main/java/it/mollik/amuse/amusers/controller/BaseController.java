package it.mollik.amuse.amusers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
	public String home() {
		LOG.info("Spring is here");
		return "Spring is here!";
	}
}
