package it.mollik.amuse.amusers.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.model.orm.Book;

/**
 * description of class {
 
 *
 * @author hendrix
 * @version 
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    
    public Page<Book> findByName(String name, Pageable page);

}
