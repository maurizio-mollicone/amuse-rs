package it.mollik.amuse.amusers.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.orm.Book;
import it.mollik.amuse.amusers.repository.BookRepository;

@Service
public class BookService extends PageableService {
    
    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> findByName(String authorName, int pageIndex, int pageSize, String sortBy) {
        
        Page<Book> booksPage = this.bookRepository.findByName(authorName, getPageable(pageIndex, pageSize, sortBy));
        logger.info("findByName {}/{} books of {}, page {}/{}", booksPage.getNumberOfElements(), booksPage.getSize(), booksPage.getTotalElements(), booksPage.getNumber(), booksPage.getTotalPages());
        return booksPage;
    }

    public Page<Book> list(int pageIndex, int pageSize, String sortBy) {
        
        Page<Book> booksPage = this.bookRepository.findAll(getPageable(pageIndex, pageSize, sortBy));
        logger.info("list {}/{} books of {}, page {}/{}", booksPage.getNumberOfElements(), booksPage.getSize(), booksPage.getTotalElements(), booksPage.getNumber(), booksPage.getTotalPages());
        return booksPage; 

    }
    
    // public Page<Book> findByAuthors(List<Author> authors, int pageIndex, int pageSize, String sortBy) {
        
    //     Page<Book> booksPage = this.bookRepository.findByAuthors(authors, getPageable(pageIndex, pageSize, sortBy));
    //     logger.info("findByAuthors {}/{} books of {}, page {}/{}, pageSize ", booksPage.getNumberOfElements(), booksPage.getSize(), booksPage.getTotalElements(), booksPage.getNumber(), booksPage.getTotalPages());
    //     return booksPage; 

    // }

    public Book findById(Long id) throws EntityNotFoundException {
        return this.bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Book create(Book author) {
        Date now = new Date();
        author.setCreateTs(now);
        author.setUpdateTs(now);
        Book result = this.bookRepository.save(author);
        logger.info("create {}", result);
        return result;
    }

    public Book save(Book author) {
        author.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        author.setUpdateTs(now);
        Book result = this.bookRepository.save(author);
        logger.info("save {}", author);
        return result;
    }
    
    public void delete(Long id) throws EntityNotFoundException {
        Book author = this.bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        this.bookRepository.delete(author);
        logger.info("delete {}", author);

    }
}
