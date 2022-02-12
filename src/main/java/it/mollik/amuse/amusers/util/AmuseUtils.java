package it.mollik.amuse.amusers.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import it.mollik.amuse.amusers.model.SearchParams;

@Component
public class AmuseUtils {
    

    public SearchParams fromSpringPage(Page page) {

        SearchParams searchParams = new SearchParams(page.getNumber(), page.getSize());
        searchParams.setTotalItems(page.getTotalElements());
        searchParams.setTotalPages(page.getTotalPages());
        searchParams.setCurrentPageSize(page.getNumberOfElements());
        return searchParams;
    }
}
