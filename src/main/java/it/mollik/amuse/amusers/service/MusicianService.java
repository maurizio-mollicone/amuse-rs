package it.mollik.amuse.amusers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.model.orm.Musician;
import it.mollik.amuse.amusers.repository.MusicianRepository;

@Service
public class MusicianService extends PageableService {
    
    private Logger logger = LoggerFactory.getLogger(MusicianService.class);

    @Autowired
    private MusicianRepository musicianRepository;

    public Page<Musician> list(int pageIndex, int pageSize, String sortBy) {
        
        Page<Musician> musiciansPage = this.musicianRepository.findAll(getPageable(pageIndex, pageSize, sortBy));
        logger.info("list {}/{} musicians of {}, page {}/{}", 
            musiciansPage.getNumberOfElements(), 
            musiciansPage.getSize(), 
            musiciansPage.getTotalElements(), 
            musiciansPage.getNumber(), 
            musiciansPage.getTotalPages());
        return musiciansPage; 

    }
}
