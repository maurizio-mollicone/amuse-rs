package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "author")
public class Author extends Person {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private List<Book> books;

    public Author(String authorName) {
        super();
        this.setName(authorName);
    }

}
