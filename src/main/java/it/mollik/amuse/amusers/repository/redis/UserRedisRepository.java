package it.mollik.amuse.amusers.repository.redis;

import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<String, Long> {
    
}
