package it.mollik.amuse.amusers;

import java.net.InetAddress;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AmuseAuthTest extends AmuseGenericTest{
    
    /**
     *
     */
    private static final String DEFAULT_PASSWORD = "1234";

    @Test
	public void anonymousAccess() throws Exception {
        getWebTestClient()
            .get()
            .uri("/amuse/v1/test/heartbeat")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK);    
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
		signupRequest.setPassword(DEFAULT_PASSWORD);

		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK)
                .jsonPath("$.data[0].userName").isEqualTo("testuser");
            

	}

	@Test
	public void emailAlreadyUsed() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("user01@localhost");
		signupRequest.setUserName("user05");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword(DEFAULT_PASSWORD);

		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
		getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

	@Test
	public void usernameAlreadyTaken() throws Exception {

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("user05@localhost");
		signupRequest.setUserName(getUser01());
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword(DEFAULT_PASSWORD);
		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isBadRequest();
    }


	@Test
	public void signinSuccess() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserName(getUser01());
		loginRequest.setPassword(DEFAULT_PASSWORD);

		AmuseRequest<LoginRequest> request = new AmuseRequest<>();
		request.setKey(new Key(getUser01()));
		request.setData(Stream.of(loginRequest).collect(Collectors.toList()));
       
        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("HTTP_CLIENT_IP", InetAddress.getLocalHost().getHostAddress())
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK);
	}

	@Test
	public void signinError() throws Exception {

		LoginRequest loginRequest = new LoginRequest();

		loginRequest.setUserName(getUser01());
		loginRequest.setPassword("1235");
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key("user01"), Stream.of(loginRequest).collect(Collectors.toList()));

        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();

	}

    // @Test
	// public void signout() throws Exception {

	// 	SignoutRequest signoutRequest = new SignoutRequest();

	// 	signoutRequest.setUserName("user01");
	// 	AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key("user01"), Stream.of(signoutRequest).collect(Collectors.toList()));

    //     getWebTestClient()
    //         .post()
    //         .uri("/amuse/v1/auth/signin")
    //         .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue("user01"))
    //         .bodyValue(request)
    //         .accept(MediaType.APPLICATION_JSON)
    //         .exchange()
    //         .expectStatus()
    //         .isUnauthorized();

	// }
}
