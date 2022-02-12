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
import it.mollik.amuse.amusers.model.AmuseEntity;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.SigninResponse;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AmuseAuthTest extends AmuseGenericTest{
    
    @Test
	public void anonymousAccess() throws Exception {
		AmuseResponse<AmuseEntity> genericResponse = getWebTestClient()
            .get()
            .uri("/amuse/v1/test/heartbeat")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<AmuseEntity>>(){})
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

	@Test
	public void signUp() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("testuser@localhost");
		signupRequest.setUserName("testuser");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword("1234");

		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
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

		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
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
		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
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

		AmuseRequest<LoginRequest> request = new AmuseRequest<>();
		request.setKey(new Key("user01"));
		request.setData(Stream.of(loginRequest).collect(Collectors.toList()));
        AmuseResponse<SigninResponse> jwtResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<SigninResponse>>(){})
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
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key("user01"), Stream.of(loginRequest).collect(Collectors.toList()));

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
