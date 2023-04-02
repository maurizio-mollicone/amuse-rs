package it.mollik.amuse.amusers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookAuthorsRepository extends PagingAndSortingRepository<Long, Long> {
    
    public Page<Long> findByAuthorId(Long  id, Pageable page);
}
