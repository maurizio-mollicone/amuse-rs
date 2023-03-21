package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import it.mollik.amuse.amusers.model.EMusicGenre;

@Entity(name = "musician")
public class Musician extends Person {

    @Column(name="genre", nullable = false)
    @Enumerated()
    private List<EMusicGenre> musicGen;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "band_musician", joinColumns = @JoinColumn(name = "musician_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "band_id", referencedColumnName = "id"))
    private List<Band> bands;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "instrument_musician", joinColumns = @JoinColumn(name = "instrument_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "musician_id", referencedColumnName = "id"))
    private List<Instrument> instruments;

    /**
     * @return List<EMusicGenre> return the musicGen
     */
    public List<EMusicGenre> getMusicGen() {
        return musicGen;
    }

    /**
     * @param musicGen the musicGen to set
     */
    public void setMusicGen(List<EMusicGenre> musicGen) {
        this.musicGen = musicGen;
    }


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

}
