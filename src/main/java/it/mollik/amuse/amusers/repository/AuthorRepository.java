package it.mollik.amuse.amusers.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.mollik.amuse.amusers.model.orm.Author;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
    
    public Iterable<Author> findByName(String name);

}
