package it.mollik.amuse.amusers.controller;

import java.util.ArrayList;
import java.util.List;

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
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.model.request.AuthorRequest;
import it.mollik.amuse.amusers.model.response.AuthorResponse;
import it.mollik.amuse.amusers.service.IAuthorService;

@RestController
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private IAuthorService authorService;

    @GetMapping(path = "/author/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorResponse list(@RequestBody AuthorRequest request) throws EntityNotFoundException {
        logger.info("/author/list");
        List<Author> authors = this.authorService.list();
        AuthorResponse response = new AuthorResponse(request.getRequestKey(), 0, "OK", authors);
        response.setAuthors(authors);
        return response;
    }

    @PutMapping(path = "/author/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthorResponse findByName(@RequestBody AuthorRequest request) throws EntityNotFoundException {
        logger.info("/author/find {}" , request);
        List<Author> authors = this.authorService.findByName(request.getAuthors().get(0).getName());
        AuthorResponse response = new AuthorResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/author/find OK {} elemts" , authors.size());
        return response;
    }

    @PostMapping(path = "/author/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorResponse create(@RequestBody AuthorRequest request) {
        Author author = this.authorService.create(request.getAuthors().get(0).getName());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        AuthorResponse response = new AuthorResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/author/created {}", author);
        return response;
    }

    @PostMapping(path = "/author/update/{artistId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorResponse save(@RequestBody AuthorRequest request, @PathVariable Integer artistId) {
        Author author = this.authorService.save(request.getAuthors().get(0));
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        AuthorResponse response = new AuthorResponse(request.getRequestKey(), 0, "OK", authors);
        logger.info("/author/created {}", author);
        return response;
    }

    @DeleteMapping(path = "/author/delete/{artistId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorResponse delete(@PathVariable Integer artistId, @RequestBody AuthorRequest request) throws EntityNotFoundException {
        this.authorService.delete(artistId);
        AuthorResponse response = new AuthorResponse(request.getRequestKey(), 0, "OK");
        logger.info("/author/delete {}", artistId);
        return response;
    }
}
