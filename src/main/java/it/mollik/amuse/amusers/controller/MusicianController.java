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
import it.mollik.amuse.amusers.model.orm.Musician;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.service.MusicianService;
import it.mollik.amuse.amusers.util.AmuseUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constants.Api.MUSICIANS_API)
public class MusicianController {
    
    private static final Logger logger = LoggerFactory.getLogger(MusicianController.class);

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AmuseUtils amuseUtils;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Musician> list(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        logger.info("{}/list", Constants.Api.MUSICIANS_API);

        Page<Musician> authorsPage = musicianService.list(pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Musician> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), searchParams, authorsPage.stream().collect(Collectors.toList()));
        logger.info("{}/list {}/{} of {} musicians, page {}/{}", Constants.Api.MUSICIANS_API, searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());
        return response;
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Musician> view(@PathVariable long id) throws ResponseStatusException {
        logger.info("{}/detail/{}", Constants.Api.MUSICIANS_API, id);
        Musician author;
        try {
            author = musicianService.findById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Musician not found. Cause: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user " + id + "not found");
        }
        AmuseResponse<Musician> response = new AmuseResponse<>(new Key(author.getName()), Stream.of(author).collect(Collectors.toList()));
        logger.info("{}/detail/{} {}",Constants.Api.MUSICIANS_API, id, response);
        return response;
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AmuseResponse<Musician> findByName(@RequestParam @NotNull String name, @RequestParam(defaultValue = "0") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sortBy) throws EntityNotFoundException {
        String decodedName = amuseUtils.decodeString(name);
        logger.info("{}/find {}", Constants.Api.MUSICIANS_API, decodedName);
        Page<Musician> authorsPage = musicianService.findByName(decodedName, pageIndex, pageSize, sortBy);
        SearchParams searchParams = amuseUtils.fromSpringPage(authorsPage);

        AmuseResponse<Musician> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER),  authorsPage.stream().collect(Collectors.toList()));
        logger.info("{}/find {}/{} of {} musicians, page {}/{}", Constants.Api.MUSICIANS_API, searchParams.getCurrentPageSize(), searchParams.getPageSize(), searchParams.getTotalItems(), searchParams.getCurrentPageIndex(), searchParams.getTotalPages());

        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Musician> create(@RequestBody @NotNull AmuseRequest<Musician> request) {
        logger.info("{}/create {}", Constants.Api.MUSICIANS_API, request.getData().get(0).getName());
        AmuseResponse<Musician> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), Stream.of(musicianService.create(request.getData().get(0))).collect(Collectors.toList()));
        logger.info("{}/create {}", Constants.Api.MUSICIANS_API, response.getData().get(0));
        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @PostMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Musician> save(@RequestBody AmuseRequest<Musician> request, @PathVariable long id) {
        logger.info("{}/update/{}", Constants.Api.MUSICIANS_API, id);
        AmuseResponse<Musician> response = new AmuseResponse<>(request.getKey(), Stream.of(musicianService.save(request.getData().get(0))).collect(Collectors.toList()));
        logger.info("{}/update/{} {}", Constants.Api.MUSICIANS_API, id, response);
        return response;
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmuseResponse<Musician> delete(@PathVariable long id) throws EntityNotFoundException {
        musicianService.delete(id);
        AmuseResponse<Musician> response = new AmuseResponse<>(new Key(Constants.SYSTEM_USER), null);
        logger.info("{}/delete/{}", Constants.Api.MUSICIANS_API, id);
        return response;
    }

}
