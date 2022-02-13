package it.mollik.amuse.amusers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import it.mollik.amuse.amusers.config.RedisProperties;
import redis.embedded.RedisServer;

@TestConfiguration
@EnableRedisRepositories
public class TestRedisConfiguration {

    private RedisServer redisServer;
    
	public TestRedisConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }


    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
