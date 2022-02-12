package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import it.mollik.amuse.amusers.model.EGenre;
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
@Entity(name = "book")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book  extends Item {
    
    @Column(name="isbn_code")
    private String isbnCode;

    @Column(name="genre", nullable = false)
    private EGenre bookGenre;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private List<Author> authors;



}
