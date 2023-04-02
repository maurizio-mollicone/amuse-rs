package it.mollik.amuse.amusers.model.orm;

import java.util.Collection;
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

import it.mollik.amuse.amusers.model.EMusicGenre;

@Entity(name = "band")
public class Band extends Item {
    
    @ManyToMany(mappedBy = "bands", fetch = FetchType.EAGER)
    private List<Musician> musicians;

    @ElementCollection(targetClass = EMusicGenre.class)
    @JoinTable(name = "band_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<EMusicGenre> genre;
    
    /**
     * @return List<Musician> return the musicians
     */
    public List<Musician> getMusicians() {
        return musicians;
    }

    /**
     * @param musicians the musicians to set
     */
    public void setMusicians(List<Musician> musicians) {
        this.musicians = musicians;
    }


    /**
     * @return Collection<EMusicGenre> return the genre
     */
    public Collection<EMusicGenre> getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(Collection<EMusicGenre> genre) {
        this.genre = genre;
    }

}
