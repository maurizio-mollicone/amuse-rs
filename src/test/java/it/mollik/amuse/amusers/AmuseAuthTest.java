package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.model.SearchParams;
import it.mollik.amuse.amusers.model.RequestKey;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.LoginResponse;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AmuseAuthTest extends AmuseGenericTest{
    
    @Test
	public void anonymousAccess() throws Exception {
		AmuseResponse genericResponse = getWebTestClient()
            .get()
            .uri("/amuse/v1/test/heartbeat")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(AmuseResponse.class)
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
		signupRequest.setEmail("testuser@localhost");
		signupRequest.setUserName("testuser");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword("1234");

		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new RequestKey("testuser"), new SearchParams(1,10), 
			Stream.of(signupRequest).collect(Collectors.toList()));
        AmuseResponse<User> response = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<User>>(){})
            .returnResult().getResponseBody();
        assertThat(response.getStatusCode()).isEqualTo(Integer.valueOf(0));
		assertThat(response.getData().get(0).getName()).isEqualTo("testuser");

	}

	@Test
	public void emailAlreadyUsed() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("user01@localhost");
		signupRequest.setUserName("user05");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword("1234");
		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new RequestKey("testuser"), new SearchParams(1,10), Stream.of(signupRequest).collect(Collectors.toList()));
		HttpStatus status = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody(AmuseResponse.class)
            .returnResult().getStatus();
        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void usernameAlreadyTaken() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("user05@localhost");
		signupRequest.setUserName("user01");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword("1234");
		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new RequestKey("testuser"), new SearchParams(1,10), Stream.of(signupRequest).collect(Collectors.toList()));

        HttpStatus status = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            //.header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<User>>(){})
            .returnResult().getStatus();
        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
	}


	@Test
	public void loginOk() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserName("user01");
		loginRequest.setPassword("1234");

		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new RequestKey("user01"), new SearchParams(1,10), Stream.of(loginRequest).collect(Collectors.toList()));

        AmuseResponse<LoginResponse> jwtResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<LoginResponse>>(){})
            .returnResult().getResponseBody();
        
        assertThat(jwtResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
	}

	// @Autowired
    // private RestTemplate restTemplate;

	@Test
	public void loginKo() throws Exception {

		LoginRequest loginRequest = new LoginRequest();

		loginRequest.setUserName("user01");
		loginRequest.setPassword("1235");
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new RequestKey("user01"), new SearchParams(1,10), Stream.of(loginRequest).collect(Collectors.toList()));

        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();

	}
}
