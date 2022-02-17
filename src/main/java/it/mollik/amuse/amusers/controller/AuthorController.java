package it.mollik.amuse.amusers.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.service.IAuthorService;
import it.mollik.amuse.amusers.util.AmuseUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/amuse/v1/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private AmuseUtils amuseUtils;
    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Author> list(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        logger.info("/amuse/v1/authors/list");

        Page<Author> authorsPage = this.authorService.list(pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), searchParams, authorsPage.stream().collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/list {}/{} of {} items, page {}/{}", searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());
        return response;
        
    }

    @PutMapping(path = "/author/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Author> findByName(@RequestParam @NotNull String name, @RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        logger.info("/amuse/v1/authors/find {}" , name);
        Page<Author> authorsPage = this.authorService.findByName(name, pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER),  authorsPage.stream().collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/find {}/{} of {} items, page {}/{}", searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());

        return response;
    }

    @PostMapping(path = "/amuse/v1/authors/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> create(@RequestBody @NotNull String name) {
        logger.info("/amuse/v1/authors/create {}", name);
        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), Stream.of(this.authorService.create(name)).collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/create {}", response.getData().get(0));
        return response;
    }

    @PostMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> save(@RequestBody AmuseRequest<Author> request, @PathVariable long id) {
        logger.info("/amuse/v1/authors/update/{}", id);
        AmuseResponse<Author> response = new AmuseResponse<>(request.getKey(), Stream.of(this.authorService.save(request.getData().get(0))).collect(Collectors.toList()));
        logger.info("/amuse/v1/authors/update/{} {}", id, response);
        return response;
    }

    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Author> delete(@PathVariable long id) throws EntityNotFoundException {
        this.authorService.delete(id);
        AmuseResponse<Author> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), null);
        logger.info("/amuse/v1/authors/delete/{}", id);
        return response;
    }
}
