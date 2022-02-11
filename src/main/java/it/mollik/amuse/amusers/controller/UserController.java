package it.mollik.amuse.amusers.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.RequestKey;
import it.mollik.amuse.amusers.model.request.UserRequest;
import it.mollik.amuse.amusers.model.response.UserResponse;
import it.mollik.amuse.amusers.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    @Autowired
    private IUserService userService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse list(Authentication authentication) throws EntityNotFoundException {
        logger.info("/users/list");
        List<User> authors = this.userService.list();
        UserResponse response = new UserResponse(new RequestKey(authentication.getName()), 0, "OK", authors);
        response.setUsers(authors);
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse view(Authentication authentication, @PathVariable int id) throws ResponseStatusException {
        logger.info("/users/detail/{}", id);
        User user;
        try {
            user = this.userService.findById(id);
        } catch (EntityNotFoundException e) {
            logger.error("User not found. Cause: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + "not found");
        }
        UserResponse response = new UserResponse(new RequestKey(user.getName()), 0, "OK", Stream.of(user).collect(Collectors.toList()));
        logger.info("/users/detail/{} {}" , id, response);
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/name/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse find(Authentication authentication, @RequestParam @NotNull String name, int pageIndex, int pageSize) throws EntityNotFoundException {
        logger.info("/users/name/search name: {}, pageIndex: {}, pageSize {}" , name, pageIndex, pageSize);
        List<User> authors = this.userService.findByName(name);
        UserResponse response = new UserResponse(new RequestKey(authentication.getName()), 0, "OK", authors);
        logger.info("/users/find OK {} elemts" , authors.size());
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/email/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse findByEmail(Authentication authentication, @RequestParam @NotNull String email, int pageIndex, int pageSize) throws EntityNotFoundException {
        logger.info("/users/email/search email: {}, pageIndex: {}, pageSize {}" , email, pageIndex, pageSize);
        List<User> authors = this.userService.findByEmail(email);
        UserResponse response = new UserResponse(new RequestKey(authentication.getName()), 0, "OK", authors);
        logger.info("/users/email/search {} elemts" , authors.size());
        return response;
    }

    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse create(@RequestBody UserRequest request) {
        List<User> authors = Stream.of(this.userService.create(request.getUsers().get(0))).collect(Collectors.toList());
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/users/create {}", response);
        return response;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse save(@RequestBody UserRequest request, @PathVariable Integer id) {
        
        List<User> authors = Stream.of(this.userService.save(request.getUsers().get(0))).collect(Collectors.toList());
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/users/update {}", response);
        return response;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse delete(@PathVariable Integer id, @RequestBody UserRequest request) throws EntityNotFoundException {
        this.userService.delete(id);
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK");
        logger.info("/users/delete {}", id);
        return response;
    }
}