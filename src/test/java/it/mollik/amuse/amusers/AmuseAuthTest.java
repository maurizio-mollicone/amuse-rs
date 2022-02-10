package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.request.RequestKey;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.GenericResponse;
import it.mollik.amuse.amusers.model.response.JwtResponse;

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
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/heartbeat", HttpMethod.GET, getHttpUtils().buildRequest(null, null), GenericResponse.class);
        //getForObject("/api/test/heartbeat", GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().getStatusCode()).isEqualTo(Integer.valueOf(0));
	}

	@Test
	public void accessDenied() throws Exception {
		ResponseEntity<String> res = this.getTestRestTemplate().exchange("/api/test/amuseuser", HttpMethod.GET, null, String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void userCanUse() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseuser", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().getStatusMessage()).startsWith(getUser01());
	}

	@Test
	public void userCannotManage() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void userCannotAdmin() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void managerCanManage() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildRequest(getManager01(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().getStatusMessage()).startsWith(getManager01());
	}

	@Test
	public void managerCannotAdmin() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getManager01(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

	}
	
	@Test
	public void adminCanAdmin() throws Exception {
		ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getAdmin(), null), GenericResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody().getStatusMessage()).startsWith(getAdmin());
	}

	@Test
	public void signUp() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setRequestKey(new RequestKey("testuser"));
		signupRequest.setEmail("testuser@localhost");
		signupRequest.setUserName("testuser");
		Set<String> roles = new HashSet<>();
		roles.add("user");
		signupRequest.setRole(roles);
		signupRequest.setPassword("1234");

		ResponseEntity<JwtResponse> res = this.getTestRestTemplate().exchange("/api/auth/signup", HttpMethod.POST, getHttpUtils().buildRequest(null, signupRequest), JwtResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(((JwtResponse) res.getBody()).getStatusCode()).isEqualTo(Integer.valueOf(0));
	}

	@Test
	public void emailAlreadyUsed() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setRequestKey(new RequestKey("testuser"));
		signupRequest.setEmail("user01@localhost");
		signupRequest.setUserName("user05");
		Set<String> roles = new HashSet<>();
		roles.add("user");
		signupRequest.setRole(roles);
		signupRequest.setPassword("1234");

		ResponseEntity<JwtResponse> res = this.getTestRestTemplate().exchange("/api/auth/signup", HttpMethod.POST, getHttpUtils().buildRequest(null, signupRequest), JwtResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(res.getBody().getStatusCode()).isEqualTo(Integer.valueOf(2));
	}

	@Test
	public void usernameAlreadyTaken() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setRequestKey(new RequestKey("testuser"));
		signupRequest.setEmail("user05@localhost");
		signupRequest.setUserName("user01");
		Set<String> roles = new HashSet<>();
		roles.add("user");
		signupRequest.setRole(roles);
		signupRequest.setPassword("1234");

		ResponseEntity<JwtResponse> res = this.getTestRestTemplate().exchange("/api/auth/signup", HttpMethod.POST, getHttpUtils().buildRequest(null, signupRequest), JwtResponse.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(res.getBody().getStatusCode()).isEqualTo(Integer.valueOf(1));
	}
}
