package it.mollik.amuse.amusers.repository.redis;

import org.springframework.data.repository.CrudRepository;

import it.mollik.amuse.amusers.model.orm.UserToken;

public interface UserTokenRedisRepository extends CrudRepository<UserToken, String> {}
