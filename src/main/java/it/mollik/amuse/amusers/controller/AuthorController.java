package it.mollik.amuse.amusers.controller;

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
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.service.AuthorService;
import it.mollik.amuse.amusers.util.AmuseUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AmuseUtils amuseUtils;
    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Author> list(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        logger.info("/amuse/v1/authors/list");

        Page<Author> authorsPage = authorService.list(pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), searchParams, authorsPage.stream().collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/list {}/{} of {} items, page {}/{}", searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());
        return response;
        
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Author> view(@PathVariable long id) throws ResponseStatusException {
        logger.info("/amuse/v1/authors/detail/{}", id);
        Author author;
        try {
            author = authorService.findById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Author not found. Cause: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + "not found");
        }
        AmuseResponse<Author> response = new AmuseResponse<>(new Key(author.getName()), Stream.of(author).collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/detail/{} {}", id, response);
        return response;
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Author> findByName(@RequestParam @NotNull String name, @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        String decodedName = amuseUtils.decodeString(name);
        logger.info("/amuse/v1/authors/find {}" , decodedName);
        Page<Author> authorsPage = authorService.findByName(decodedName, pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER),  authorsPage.stream().collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/find {}/{} of {} items, page {}/{}", searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());

        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> create(@RequestBody @NotNull AmuseRequest<Author> request) {
        logger.info("/amuse/v1/authors/create {}", request.getData().get(0).getName());
        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), Stream.of(authorService.create(request.getData().get(0))).collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/create {}", response.getData().get(0));
        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @PostMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> save(@RequestBody AmuseRequest<Author> request, @PathVariable long id) {
        logger.info("/amuse/v1/authors/update/{}", id);
        AmuseResponse<Author> response = new AmuseResponse<>(request.getKey(), Stream.of(authorService.save(request.getData().get(0))).collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/update/{} {}", id, response);
        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> delete(@PathVariable long id) throws EntityNotFoundException {
        authorService.delete(id);
        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), null);
        logger.info("/amuse/v1/authors/delete/{}", id);
        return response;
    }
}
