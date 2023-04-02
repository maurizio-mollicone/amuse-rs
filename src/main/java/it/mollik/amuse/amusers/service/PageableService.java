package it.mollik.amuse.amusers.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableService {
    
    public Pageable getPageable(int pageIndex, int pageSize, String sortBy) {
        return (sortBy != null && !sortBy.isEmpty()) ? 
            PageRequest.of(pageIndex, pageSize, Sort.by(sortBy).ascending()) : 
            PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
    }
}
