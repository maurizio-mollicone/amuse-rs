package it.mollik.amuse.amusers.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }
}