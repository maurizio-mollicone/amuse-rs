package it.mollik.amuse.amusers.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.orm.Author;

@Service
public interface IAuthorService {
    
    public Page<Author> findByName(String authorName, int pageIndex, int pageSize, String sortBy) throws EntityNotFoundException;

    public Page<Author> list(int pageIndex, int pageSize, String sortBy) throws EntityNotFoundException;

    public Author findById(Long id) throws EntityNotFoundException;

    public Author create(Author author);

    public Author save(Author author);
    
    public void delete(Long id) throws EntityNotFoundException;
}
