package it.mollik.amuse.amusers.model.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EGenre;

@Entity(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book  extends Item {
    
    @Column(name="isbn_code")
    private String isbnCode;

    @Column(name = "pub_year")
    private Long pubYear;

    @Column(name="genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private EGenre bookGenre;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private List<Author> authors;

    public Book() {}

    public Book(String title, String description, Long pubYear, Date createTs, Date updateTs, EEntityStatus status,
            String isbnCode, EGenre bookGenre, List<Author> authors) {
        super(title, description, createTs, updateTs, status);
        this.isbnCode = isbnCode;
        this.bookGenre = bookGenre;
        this.authors = authors;
        this.pubYear = pubYear;
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


    /**
     * @return Long return the pubYear
     */
    public Long getPubYear() {
        return pubYear;
    }

    /**
     * @param pubYear the pubYear to set
     */
    public void setPubYear(Long pubYear) {
        this.pubYear = pubYear;
    }

}
