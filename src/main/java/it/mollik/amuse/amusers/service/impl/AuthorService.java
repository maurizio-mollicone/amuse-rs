package it.mollik.amuse.amusers.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.repository.AuthorRepository;
import it.mollik.amuse.amusers.service.IAuthorService;

@Service
public class AuthorService implements IAuthorService {

    private Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findByName(String authorName) throws EntityNotFoundException {
        List<Author> authors = new ArrayList<>();
        this.authorRepository.findByName(authorName).forEach(authors::add);
        if (authors.isEmpty()) {
            logger.error("EntityNotFoundException {}", authorName);
            throw new EntityNotFoundException(String.format("EntityNotFoundException %s", authorName));
        }
        logger.info("findByName {}, {} results", authorName, authors.size());
        return authors;
    }

    @Override
    public List<Author> list() throws EntityNotFoundException {
        
        List<Author> authors = new ArrayList<>();
        Pageable sortedByName = PageRequest.of(0, 3, Sort.by("name"));
        this.authorRepository.findAll(sortedByName).forEach(authors::add);
        if (authors.isEmpty()) {
            logger.error("EntityNotFoundException");
            throw new EntityNotFoundException(StringUtils.EMPTY);
        }
        logger.info("list {} results", authors.size());

        return authors;
    }

    @Override
    public Author findById(Integer artistId) throws EntityNotFoundException {
        return this.authorRepository.findById(artistId).orElseThrow(() -> new EntityNotFoundException(artistId.toString()));
    }

    @Override
    public Author create(String authorName) {
        Author author = new Author(authorName);
        Date now = new Date();
        author.setCreateTs(now);
        author.setUpdateTs(now);
        Author result = this.authorRepository.save(author);
        logger.info("create {}", result);
        return result;
    }

    @Override
    public Author save(Author author) {
        author.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        author.setUpdateTs(now);
        Author result = this.authorRepository.save(author);
        logger.info("save {}", author);
        return result;
    }
    
    public void delete(Integer authorId) throws EntityNotFoundException {
        Author author = this.authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException(authorId.toString()));
        this.authorRepository.delete(author);
        logger.info("delete {}", author);

    }

}
