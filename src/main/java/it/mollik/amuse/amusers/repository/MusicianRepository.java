package it.mollik.amuse.amusers.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.mollik.amuse.amusers.model.EMusicGenre;
import it.mollik.amuse.amusers.model.orm.Band;
import it.mollik.amuse.amusers.model.orm.Musician;

@Repository
public interface MusicianRepository extends PagingAndSortingRepository<Musician, Long> {
    
    public Page<Musician> findByName(String name, Pageable page);

    @Query("SELECT m FROM Musician m WHERE m.genre in :genres")
    public Page<Musician> findByGenre(Collection<EMusicGenre> genres, Pageable page);
    
    @Query("SELECT m FROM Musician m WHERE m.bands in :bands")
    public Page<Musician> findByBands(Collection<Band> bands, Pageable page);

}
