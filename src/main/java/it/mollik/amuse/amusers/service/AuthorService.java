package it.mollik.amuse.amusers.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.repository.AuthorRepository;

@Service
public class AuthorService {

    private Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    public Page<Author> findByName(String authorName, int pageIndex, int pageSize, String sortBy) {
        
        Pageable page = (sortBy != null && !sortBy.isEmpty()) ? PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()) : PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        Page<Author> authorsPage = this.authorRepository.findByName(authorName, page);
        logger.info("findByName {}/{} authors of {}, page {}/{}, pageSize ", authorsPage.getNumberOfElements(), authorsPage.getSize(), authorsPage.getTotalElements(), authorsPage.getNumber(), authorsPage.getTotalPages());
        return authorsPage;
    }

    public Page<Author> list(int pageIndex, int pageSize, String sortBy) {
        
        Pageable page = (sortBy != null && !sortBy.isEmpty()) ? PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()) : PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        Page<Author> authorsPage = this.authorRepository.findAll(page);
        logger.info("list {}/{} authors of {}, page {}/{}, pageSize ", authorsPage.getNumberOfElements(), authorsPage.getSize(), authorsPage.getTotalElements(), authorsPage.getNumber(), authorsPage.getTotalPages());
        return authorsPage; 

    }

    public Author findById(Long id) throws EntityNotFoundException {
        return this.authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Author create(Author author) {
        Date now = new Date();
        author.setCreateTs(now);
        author.setUpdateTs(now);
        Author result = this.authorRepository.save(author);
        logger.info("create {}", result);
        return result;
    }

    public Author save(Author author) {
        author.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        author.setUpdateTs(now);
        Author result = this.authorRepository.save(author);
        logger.info("save {}", author);
        return result;
    }
    
    public void delete(Long id) throws EntityNotFoundException {
        Author author = this.authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        this.authorRepository.delete(author);
        logger.info("delete {}", author);

    }

}
