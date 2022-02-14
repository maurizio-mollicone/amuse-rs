package it.mollik.amuse.amusers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.config.EmbeddedRedisTestConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Import(EmbeddedRedisTestConfiguration.class)
@ActiveProfiles(value = "test")
class AmuseRsApplicationTests {

	@Test
	void contextLoads() {
	}

}
