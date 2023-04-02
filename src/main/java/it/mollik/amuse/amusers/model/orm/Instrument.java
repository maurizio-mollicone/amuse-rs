package it.mollik.amuse.amusers.model.orm;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity(name = "instrument")
public class Instrument extends Item {
    
    @ManyToMany(mappedBy = "instruments", fetch = FetchType.EAGER)
    private List<Musician> musicians;

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
