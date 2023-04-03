package it.mollik.amuse.amusers.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import it.mollik.amuse.amusers.model.orm.Musician;

@Repository
public interface MusicianRepository extends PagingAndSortingRepository<Musician, Long> {
    
}
