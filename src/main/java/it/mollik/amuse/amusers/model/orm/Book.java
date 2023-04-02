package it.mollik.amuse.amusers.model.orm;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.EBookGenre;
import it.mollik.amuse.amusers.model.EEntityStatus;

@Entity(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book extends Item {
    
    @Column(name="isbn_code")
    private String isbnCode;

    @Column(name = "pub_year")
    private Long pubYear;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private List<Author> authors;

    @ElementCollection(targetClass = EBookGenre.class)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<EBookGenre> genre;

    public Book() {}

    public Book(String title, String description, Long pubYear, Date createTs, Date updateTs, EEntityStatus status,
            String isbnCode, List<Author> authors) {
        super(title, description, createTs, updateTs, status);
        this.isbnCode = isbnCode;
        this.authors = authors;
        this.pubYear = pubYear;
    }

    public Book(String isbnCode, List<Author> authors) {
        this.isbnCode = isbnCode;
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


    /**
     * @return Collection<EBookGenre> return the genre
     */
    public Collection<EBookGenre> getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(Collection<EBookGenre> genre) {
        this.genre = genre;
    }

}
