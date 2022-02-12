package it.mollik.amuse.amusers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AmuseRsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmuseRsApplication.class, args);
	}

}
