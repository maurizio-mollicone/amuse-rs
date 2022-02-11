package it.mollik.amuse.amusers.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.UserRequest;
import it.mollik.amuse.amusers.model.response.UserResponse;
import it.mollik.amuse.amusers.service.IUserService;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    @Autowired
    private IUserService userService;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse list(@RequestBody UserRequest request) throws EntityNotFoundException {
        logger.info("/user/list");
        List<User> authors = this.userService.list();
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        response.setUsers(authors);
        return response;
    }

    @PutMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse findByName(@RequestBody UserRequest request) throws EntityNotFoundException {
        logger.info("/user/find {}" , request);
        List<User> authors = this.userService.findByName(request.getUsers().get(0).getName());
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/user/find OK {} elemts" , authors.size());
        return response;
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse signup(@RequestBody UserRequest request) {
        List<User> authors = Stream.of(this.userService.create(request.getUsers().get(0))).collect(Collectors.toList());
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/user/created {}", response);
        return response;
    }

    @PostMapping(path = "/user/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse save(@RequestBody UserRequest request, @PathVariable Integer id) {
        
        List<User> authors = Stream.of(this.userService.save(request.getUsers().get(0))).collect(Collectors.toList());
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/user/created {}", response);
        return response;
    }

    @DeleteMapping(path = "/user/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponse delete(@PathVariable Integer id, @RequestBody UserRequest request) throws EntityNotFoundException {
        this.userService.delete(id);
        UserResponse response = new UserResponse(request.getRequestKey(), 0, "OK");
        logger.info("/user/delete {}", id);
        return response;
    }
}