package it.mollik.amuse.amusers.model.orm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import it.mollik.amuse.amusers.model.EEntityStatus;

@Entity(name = "author")
public class Author extends Person {
    
    @Column(name = "first_name", length = 500, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 500, nullable = false)
    private String lastName;
    
    @Lob
    @Column(name = "biography", length=100000)
    private byte[] biography;

    @Column(name = "birth_dt", length = 500)
    private Date birthDate;
    
    @Column(name = "death_dt", length = 500)
    private Date deathDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private List<Book> books;

    public Author() {}

    public Author(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.setName(firstName + " " + lastName);
        this.setStatus(EEntityStatus.INSERT);
    }

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


    /**
     * @return String return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return byte[] return the biography
     */
    public byte[] getBiography() {
        return biography;
    }

    /**
     * @param biography the biography to set
     */
    public void setBiography(byte[] biography) {
        this.biography = biography;
    }

    /**
     * @return Date return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return Date return the deathDate
     */
    public Date getDeathDate() {
        return deathDate;
    }

    /**
     * @param deathDate the deathDate to set
     */
    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

}
