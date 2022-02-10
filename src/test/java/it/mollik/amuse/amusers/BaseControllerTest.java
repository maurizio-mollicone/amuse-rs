package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.util.JwtUtils;


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class BaseControllerTest {
	
    @Value("${amuse.security.admin:admin}")
	private String admin;

	@Value("${amuse.security.manager01:manager01}")
	private String manager01;

	@Value("${amuse.security.user01:user01}")
	private String user01;
	
	@Value("${amuse.security.defaultpassword:1234}")
	private String defaultPassword;
    
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JwtUtils jwtUtils;

    @Test
	public void amuseYourself() throws Exception {
		String body = this.testRestTemplate.getForObject("/api/test/heartbeat", String.class);
		assertThat(body).isEqualTo("aMuse yourself!");
	}

	private HttpEntity<String> authorize(String userName) {
		String token = jwtUtils.createJwtTestToken("user01");
        assertThat(token).isNotEmpty();
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", bearerAuthorization(token));
		return new HttpEntity<String>(headers);
	}

	private String bearerAuthorization(String token) {
		return "Bearer " + token;
	}

	@Test
    public void jwtAuthorization() throws Exception {
        
        ResponseEntity<String> res = this.testRestTemplate.exchange("/api/test/amuseuser", HttpMethod.GET, authorize(user01), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).startsWith("user01");
    }


	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
}
