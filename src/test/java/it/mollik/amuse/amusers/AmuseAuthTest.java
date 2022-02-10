package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AmuseAuthTest extends AmuseGenericTest{
    
    // @Value("${amuse.security.admin:admin}")
	// private String admin;

	// @Value("${amuse.security.manager01:manager01}")
	// private String manager01;

	// @Value("${amuse.security.user01:user01}")
	// private String user01;
	
	// @Value("${amuse.security.defaultpassword:1234}")
	// private String defaultPassword;
    
	// @Autowired
	// private TestRestTemplate testRestTemplate;

	// @Autowired
	// private HttpUtils httpUtils;
    
    @Test
	public void anonymousAccess() throws Exception {
		String body = this.getTestRestTemplate().getForObject("/api/test/heartbeat", String.class);
		assertThat(body).isEqualTo("aMuse yourself!");
	}

	@Test
	public void accessDenied() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseuser", HttpMethod.GET, null, String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void userCanUse() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseuser", HttpMethod.GET, getHttpUtils().buildHeaders(getUser01()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).startsWith(getUser01());
	}

	@Test
	public void userCannotManage() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildHeaders(getUser01()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void userCannotAdmin() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildHeaders(getUser01()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void managerCanManage() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildHeaders(getManager01()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).startsWith(getManager01());
	}

	@Test
	public void managerCannotAdmin() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildHeaders(getManager01()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

	}
	
	@Test
	public void adminCanAdmin() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildHeaders(getAdmin()), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).startsWith(getAdmin());
	}
}
