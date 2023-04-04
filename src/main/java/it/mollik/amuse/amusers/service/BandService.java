package it.mollik.amuse.amusers.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.mollik.amuse.amusers.exceptions.EntityNotFoundException;
import it.mollik.amuse.amusers.model.EEntityStatus;
import it.mollik.amuse.amusers.model.orm.Band;
import it.mollik.amuse.amusers.repository.BandRepository;

@Service
public class BandService extends PageableService {
    
    private Logger logger = LoggerFactory.getLogger(BandService.class);

    @Autowired
    private BandRepository bandRepository;

    public Page<Band> findByName(String name, int pageIndex, int pageSize, String sortBy) {
        
        Page<Band> bandsPage = this.bandRepository.findByName(name, getPageable(pageIndex, pageSize, sortBy));
        logger.info("findByName {}/{} bands of {}, page {}/{}", bandsPage.getNumberOfElements(), bandsPage.getSize(), bandsPage.getTotalElements(), bandsPage.getNumber(), bandsPage.getTotalPages());
        return bandsPage;
    }

    public Page<Band> list(int pageIndex, int pageSize, String sortBy) {
        
        Page<Band> bandsPage = this.bandRepository.findAll(getPageable(pageIndex, pageSize, sortBy));
        logger.info("list {}/{} bands of {}, page {}/{}", bandsPage.getNumberOfElements(), bandsPage.getSize(), bandsPage.getTotalElements(), bandsPage.getNumber(), bandsPage.getTotalPages());
        return bandsPage; 

    }
    
    public Band findById(Long id) throws EntityNotFoundException {
        return this.bandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public Band create(Band band) {
        Date now = new Date();
        band.setCreateTs(now);
        band.setUpdateTs(now);
        Band result = this.bandRepository.save(band);
        logger.info("create {}", result);
        return result;
    }

    public Band save(Band band) {
        band.setStatus(EEntityStatus.UPDATE);
        Date now = new Date();
        band.setUpdateTs(now);
        Band result = this.bandRepository.save(band);
        logger.info("save {}", band);
        return result;
    }
    
    public void delete(Long id) throws EntityNotFoundException {
        Band band = this.bandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        this.bandRepository.delete(band);
        logger.info("delete {}", band);

    }
}
