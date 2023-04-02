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

@Entity(name = "musician")
public class Musician extends Person {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "band_musician", joinColumns = @JoinColumn(name = "musician_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "band_id", referencedColumnName = "id"))
    private List<Band> bands;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "instrument_musician", joinColumns = @JoinColumn(name = "instrument_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "musician_id", referencedColumnName = "id"))
    private List<Instrument> instruments;

    @ElementCollection(targetClass = EMusicGenre.class)
    @JoinTable(name = "musician_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<EMusicGenre> genre;

    /**
     * @return List<Band> return the bands
     */
    public List<Band> getBands() {
        return bands;
    }

    /**
     * @param bands the bands to set
     */
    public void setBands(List<Band> bands) {
        this.bands = bands;
    }


    /**
     * @return List<Instrument> return the instruments
     */
    public List<Instrument> getInstruments() {
        return instruments;
    }

    /**
     * @param instruments the instruments to set
     */
    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
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
