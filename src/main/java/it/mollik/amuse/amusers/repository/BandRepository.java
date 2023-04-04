package it.mollik.amuse.amusers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.mollik.amuse.amusers.model.orm.Band;

public interface BandRepository extends PagingAndSortingRepository<Band, Long> {
    
    public Page<Band> findByName(String name, Pageable page);

}
