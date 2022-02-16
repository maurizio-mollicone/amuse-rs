package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "author")
public class Author extends Person {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private List<Book> books;

    public Author() {}

    public Author(String authorName) {
        super();
        this.setName(authorName);
    }

    public Author(String authorName, List<Book> books) {
        this(authorName);
        this.books = books;
    }

    /**
     * @return List<Book> return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
