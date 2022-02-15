package it.mollik.amuse.amusers.model.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book  extends Item {
    
    @Column(name="isbn_code")
    private String isbnCode;

    @Column(name="genre", nullable = false)
    private EGenre bookGenre;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private List<Author> authors;

    public Book(String title, String description, Date year, Date createTs, Date updateTs, EEntityStatus status,
            String isbnCode, EGenre bookGenre, List<Author> authors) {
        super(title, description, year, createTs, updateTs, status);
        this.isbnCode = isbnCode;
        this.bookGenre = bookGenre;
        this.authors = authors;
    }

    public Book(String isbnCode, EGenre bookGenre, List<Author> authors) {
        this.isbnCode = isbnCode;
        this.bookGenre = bookGenre;
        this.authors = authors;
        Date now = new Date();
        this.setCreateTs(now);
        this.setUpdateTs(now);
    }

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
     * @return EGenre return the bookGenre
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
