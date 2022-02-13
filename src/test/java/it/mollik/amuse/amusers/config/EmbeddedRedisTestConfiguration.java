package it.mollik.amuse.amusers.config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@TestConfiguration
@EnableRedisRepositories
public class EmbeddedRedisTestConfiguration {

    private Logger logger = LoggerFactory.getLogger(EmbeddedRedisTestConfiguration.class);

    private final redis.embedded.RedisServer redisServer;
    
    @Value("${spring.redis.port}")
    private final int redisPort;

    public EmbeddedRedisTestConfiguration(@Value("${spring.redis.port}") final int redisPort) throws IOException {
        this.redisPort = redisPort;
        this.redisServer = new redis.embedded.RedisServer(redisPort);
    }

    @PostConstruct
    public void startRedis() {
        logger.info("start embedded Redis server on localhost:{}", redisPort);
        this.redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        logger.info("stop embedded Redis server on localhost:{}", redisPort);
        this.redisServer.stop();
    }
}
