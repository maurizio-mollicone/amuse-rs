package it.mollik.amuse.amusers.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.IAmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.orm.Role;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.service.UserService;
import it.mollik.amuse.amusers.util.AmuseUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private AmuseUtils amuseUtils;
    
    @GetMapping("/principal")
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<User> list(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        logger.info("/amuse/v1/users/list");

        Page<User> usersPage = this.userService.list(pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(usersPage);

        AmuseResponse<User> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), searchParams, usersPage.stream().collect(Collectors.toList()));
        logger.info("/amuse/v1/users/list {}/{} of {} items, page {}/{}", searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<User> view(@PathVariable long id) throws ResponseStatusException {
        logger.info("/amuse/v1/users/detail/{}", id);
        User user;
        try {
            user = this.userService.findById(id);
        } catch (EntityNotFoundException e) {
            logger.error("User not found. Cause: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + "not found");
        }
        AmuseResponse<User> response = new AmuseResponse<>(new Key(user.getName()), Stream.of(user).collect(Collectors.toList()));
        logger.info("/amuse/v1/users/detail/{} {}" , id, response);
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/name/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<User> find(@RequestParam @NotNull String name) throws EntityNotFoundException {
        logger.info("/amuse/v1/users/name/search name: {}" , name);
        AmuseResponse<User> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), this.userService.findByName(name));
        logger.info("/amuse/v1/users/find OK {}" , response);
        return response;
    }

    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/email/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<User> findByEmail(@RequestParam @NotNull String email) throws EntityNotFoundException {
        logger.info("/amuse/v1/users/email/search email: {}" , email);
        AmuseResponse<User> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), this.userService.findByEmail(email));
        logger.info("/amuse/v1/users/email/search {} elements" , response.getData().size());
        return response;
    }

    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<User> create(@RequestBody AmuseRequest<User> request) {
        List<User> users = Stream.of(this.userService.create(request.getData().get(0))).collect(Collectors.toList());
        AmuseResponse<User> response = new AmuseResponse<>(request.getKey(), users);
        logger.info("/amuse/v1/users/create {}", response);
        return response;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<User> save(@PathVariable @NotNull int id, @RequestBody AmuseRequest<User> request) {
        List<User> users = Stream.of(this.userService.save(request.getData().get(0))).collect(Collectors.toList());
        AmuseResponse<User> response = new AmuseResponse<>(request.getKey(), users);
        logger.info("/amuse/v1/users/update {}", response);
        return response;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<IAmuseEntity> delete(@PathVariable long id) throws EntityNotFoundException {
        this.userService.delete(id);
        AmuseResponse<IAmuseEntity> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), null);
        logger.info("/amuse/v1/users/delete {}", response);
        return response;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<User> addRole(@PathVariable long id, @RequestBody AmuseRequest<Role> request) throws EntityNotFoundException {
        User user = this.userService.findById(id);
        Role role = request.getData().get(0);
        Date now = new Date();
        role.setCreateTs(now);
        role.setUser(user);
        user.addRole(role);
        user.setUpdateTs(now);
        User updatedUser = this.userService.save(user);
        AmuseResponse<User> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), Stream.of(updatedUser).collect(Collectors.toList()));
        logger.info("/amuse/v1/users/{}/role {}", id, response);
        return response;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<User> removeRole(@PathVariable long id, @RequestBody AmuseRequest<Role> request) throws EntityNotFoundException {
        User user = this.userService.deleteRole(request.getData().get(0));
        AmuseResponse<User> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), Stream.of(user).collect(Collectors.toList()));
        logger.info("/amuse/v1/users/{}/role {}", id, response);
        return response;
    }
}