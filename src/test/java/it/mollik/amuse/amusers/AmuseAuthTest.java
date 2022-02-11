package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.RequestKey;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.GenericResponse;
import it.mollik.amuse.amusers.model.response.JwtResponse;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AmuseAuthTest extends AmuseGenericTest{
    
    @Test
	public void anonymousAccess() throws Exception {
		GenericResponse genericResponse = getWebTestClient()
            .get()
            .uri("/amuse/v1/test/heartbeat")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GenericResponse.class)
            .returnResult().getResponseBody();
        
        assertThat(genericResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
        
	}

	@Test
	public void accessDenied() throws Exception {
        getWebTestClient()
            .get()
            .uri("/api/v1/test/amuseuser")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();
		
	}

	// @Test
	// public void userCanUse() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseuser", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
	// 	assertThat(res.getBody().getStatusMessage()).startsWith(getUser01());
	// }

	// @Test
	// public void userCannotManage() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	// }

	// @Test
	// public void userCannotAdmin() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getUser01(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	// }

	// @Test
	// public void managerCanManage() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amusemanager", HttpMethod.GET, getHttpUtils().buildRequest(getManager01(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
	// 	assertThat(res.getBody().getStatusMessage()).startsWith(getManager01());
	// }

	// @Test
	// public void managerCannotAdmin() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getManager01(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

	// }
	
	// @Test
	// public void adminCanAdmin() throws Exception {
	// 	ResponseEntity<GenericResponse> res = this.getTestRestTemplate().exchange("/api/test/amuseadmin", HttpMethod.GET, getHttpUtils().buildRequest(getAdmin(), null), GenericResponse.class);
	// 	assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
	// 	assertThat(res.getBody().getStatusMessage()).startsWith(getAdmin());
	// }

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
        GenericResponse genericResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(signupRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GenericResponse.class)
            .returnResult().getResponseBody();
        assertThat(genericResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
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

		HttpStatus status = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(signupRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody(GenericResponse.class)
            .returnResult().getStatus();
        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
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
        HttpStatus status = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(signupRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody(GenericResponse.class)
            .returnResult().getStatus();
        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
	}


	@Test
	public void loginOk() throws Exception {

		LoginRequest loginRequest = new LoginRequest();

		loginRequest.setRequestKey(new RequestKey("user01"));
		loginRequest.setUserName("user01");
		loginRequest.setPassword("1234");
        JwtResponse jwtResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(loginRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(JwtResponse.class)
            .returnResult().getResponseBody();
        
        assertThat(jwtResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
	}

	// @Autowired
    // private RestTemplate restTemplate;

	@Test
	public void loginKo() throws Exception {

		LoginRequest loginRequest = new LoginRequest();

		loginRequest.setRequestKey(new RequestKey("user01"));
		loginRequest.setUserName("user01");
		loginRequest.setPassword("1235");

        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(loginRequest)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();

	}
}
