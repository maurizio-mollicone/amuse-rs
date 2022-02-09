package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.EGenre;

@Entity(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book  extends Item {
    
    @Column(name="isbn_code")
    private String isbnCode;

    @Column(name="genre", nullable = false)
    private EGenre bookGenre;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private List<Author> authors;



    /**
     * @return String return the isbnCode
     */
    public String getIsbnCode() {
        return isbnCode;
    }

    /**
     * @param isbnCode the isbnCode to set
     */
    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    /**
     * @return BookGenre return the bookGenre
     */
    public EGenre getBookGenre() {
        return bookGenre;
    }

    /**
     * @param bookGenre the bookGenre to set
     */
    public void setBookGenre(EGenre bookGenre) {
        this.bookGenre = bookGenre;
    }

    /**
     * @return List<Author> return the authors
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

}
