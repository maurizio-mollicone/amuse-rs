package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import it.mollik.amuse.amusers.model.EMusicGenre;

@Entity(name = "band")
public class Band extends Item {
    
    @Column(name="genre", nullable = false)
    @Enumerated()
    private List<EMusicGenre> musicGen;

    @ManyToMany(mappedBy = "bands", fetch = FetchType.EAGER)
    private List<Musician> musicians;

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

}
