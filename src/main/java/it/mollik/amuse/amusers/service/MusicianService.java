package it.mollik.amuse.amusers.service;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.EMusicGenre;
import it.mollik.amuse.amusers.model.orm.Band;
import it.mollik.amuse.amusers.model.orm.Musician;
import it.mollik.amuse.amusers.repository.MusicianRepository;

@Service
public class MusicianService extends PageableService {
    
    private Logger logger = LoggerFactory.getLogger(MusicianService.class);

    @Autowired
    private MusicianRepository musicianRepository;

    public Page<Musician> findByName(String musicianName, int pageIndex, int pageSize, String sortBy) {
        
        Page<Musician> musiciansPage = this.musicianRepository.findByName(musicianName, getPageable(pageIndex, pageSize, sortBy));
        logger.info("findByName {}/{} musicians of {}, page {}/{}", 
            musiciansPage.getNumberOfElements(), musiciansPage.getSize(), musiciansPage.getTotalElements(), 
            musiciansPage.getNumber(), musiciansPage.getTotalPages());
        return musiciansPage;
    }

    public Page<Musician> list(int pageIndex, int pageSize, String sortBy) {
        
        Page<Musician> musiciansPage = this.musicianRepository.findAll(getPageable(pageIndex, pageSize, sortBy));
        logger.info("list {}/{} musicians of {}, page {}/{}", 
            musiciansPage.getNumberOfElements(), musiciansPage.getSize(), musiciansPage.getTotalElements(), 
            musiciansPage.getNumber(), musiciansPage.getTotalPages());
        return musiciansPage; 

    }

    public Page<Musician> findByGenre(EMusicGenre genre, int pageIndex, int pageSize, String sortBy) {
        
        Page<Musician> musiciansPage = this.musicianRepository.findByGenre(Stream.of(genre).collect(Collectors.toList()), getPageable(pageIndex, pageSize, sortBy));
        logger.info("findByGenre {}/{} musicians of {}, page {}/{}", 
            musiciansPage.getNumberOfElements(), musiciansPage.getSize(), musiciansPage.getTotalElements(), 
            musiciansPage.getNumber(), musiciansPage.getTotalPages());
        return musiciansPage;
    }

    public Page<Musician> findByBand(Band band, int pageIndex, int pageSize, String sortBy) {
        
        Page<Musician> musiciansPage = this.musicianRepository.findByBands(Stream.of(band).collect(Collectors.toList()), getPageable(pageIndex, pageSize, sortBy));
        logger.info("findByBands {}/{} musicians of {}, page {}/{}", 
            musiciansPage.getNumberOfElements(), musiciansPage.getSize(), musiciansPage.getTotalElements(), 
            musiciansPage.getNumber(), musiciansPage.getTotalPages());
        return musiciansPage;
    }

    public Musician findById(Long id) throws EntityNotFoundException {
        return this.musicianRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Musician create(Musician musician) {
        Date now = new Date();
        musician.setCreateTs(now);
        musician.setUpdateTs(now);
        Musician result = this.musicianRepository.save(musician);
        logger.info("create {}", result);
        return result;
    }

    public Musician save(Musician musician) {
        musician.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        musician.setUpdateTs(now);
        Musician result = this.musicianRepository.save(musician);
        logger.info("save {}", musician);
        return result;
    }
    
    public void delete(Long id) throws EntityNotFoundException {
        Musician musician = this.musicianRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        this.musicianRepository.delete(musician);
        logger.info("delete {}", musician);
    }
    
}
