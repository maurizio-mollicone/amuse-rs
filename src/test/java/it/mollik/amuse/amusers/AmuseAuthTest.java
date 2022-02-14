package it.mollik.amuse.amusers;

import java.net.InetAddress;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignoutRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.SigninResponse;
import it.mollik.amuse.amusers.model.response.SignoutResponse;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse base tests")
public class AmuseAuthTest extends AmuseGenericTest{
    
    private Logger logger = LoggerFactory.getLogger(AmuseAuthTest.class);

    /**
     *
     */
    private static final String DEFAULT_PASSWORD = "1234";

    @Test
    @Order(1)
    @DisplayName("Anonymous access")
	public void anonymousAccess() throws Exception {
        logger.info("Start anonymousAccess");
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
    @Order(2)
    @DisplayName("Access denied")
	public void accessDenied() throws Exception {
        logger.info("Start accessDenied");
        getWebTestClient()
            .get()
            .uri("/api/v1/test/amuseuser")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();
		
	}

	@Test
    @Order(3)
    @DisplayName("Signup")
	public void signup() throws Exception {
        logger.info("Start signup");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setEmail("testuser@localhost");
		signupRequest.setUserName("testuser");
		signupRequest.setRole(Stream.of("user").collect(Collectors.toList()));
		signupRequest.setPassword(DEFAULT_PASSWORD);

		AmuseRequest<SignupRequest> request = new AmuseRequest<>();
		request.setKey(new Key("testuser"));
		request.setData(Stream.of(signupRequest).collect(Collectors.toList()));
        AmuseResponse<User> response = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signup")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            // .expectBody()
            //     .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK)
            //     .jsonPath("$.data[0].userName").isEqualTo("testuser");
            .expectBody(new ParameterizedTypeReference<AmuseResponse<User>>() {}).returnResult().getResponseBody();
        logger.info("response {}", response.toJSONString());
	}

	@Test
    @Order(4)
    @DisplayName("Signup error - email already used")
	public void emailAlreadyUsed() throws Exception {
        logger.info("Start emailAlreadyUsed");

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
    @Order(5)
    @DisplayName("Signup error - username already used")
	public void usernameAlreadyTaken() throws Exception {
        logger.info("Start usernameAlreadyTaken");

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
    @Order(6)
	public void signinError() throws Exception {
        logger.info("Start signinError");

		LoginRequest loginRequest = new LoginRequest();

		loginRequest.setUserName(getUser01());
		loginRequest.setPassword("1235");
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key("user01"), Stream.of(loginRequest).collect(Collectors.toList()));

        getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isUnauthorized();

	}

	// @Test
    // @Order(7)
    // @DisplayName("Signin")
	// public void signinSuccess() throws Exception {
    //     logger.info("Start Signin");

	// 	LoginRequest loginRequest = new LoginRequest();
	// 	loginRequest.setUserName(getUser01());
	// 	loginRequest.setPassword(DEFAULT_PASSWORD);

	// 	AmuseRequest<LoginRequest> request = new AmuseRequest<>();
	// 	request.setKey(new Key(getUser01()));
	// 	request.setData(Stream.of(loginRequest).collect(Collectors.toList()));
       
    //     getWebTestClient()
    //         .post()
    //         .uri("/amuse/v1/auth/signin")
    //         .header("HTTP_CLIENT_IP", InetAddress.getLocalHost().getHostAddress())
    //         .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
    //         .bodyValue(request)
    //         .accept(MediaType.APPLICATION_JSON)
    //         .exchange()
    //         .expectStatus()
    //         .isOk()
    //         .expectBody()
    //             .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK);
	// }

	

    @ParameterizedTest
    @ValueSource(strings = {"user01"})
    @Order(7)
	public void signinAndSignout(String username) throws Exception {
        logger.info("START test signinAndSignout");
        
        logger.info("signin {}", username);

		LoginRequest loginRequest = new LoginRequest(username, DEFAULT_PASSWORD);
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key(username), Stream.of(loginRequest).collect(Collectors.toList()));
		
        AmuseResponse<SigninResponse> signinResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signin")
            .header("HTTP_CLIENT_IP", InetAddress.getLocalHost().getHostAddress())
            .header("Authorization", getHttpUtils().buildAuthHeaderValue(username, ERole.USER.getValue()))
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<SigninResponse>>() {}).returnResult().getResponseBody();
            // .expectBody()
            //     .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK);

        String token = signinResponse.getData().get(0).getToken();
        logger.info("signin response {}", signinResponse.toJSONString());

        logger.info("signout {}", username);

		SignoutRequest signoutRequest = new SignoutRequest(username);
		AmuseRequest<SignoutRequest> request2 = new AmuseRequest<>(new Key(username), Stream.of(signoutRequest).collect(Collectors.toList()));

        AmuseResponse<SignoutResponse> signoutResponse = getWebTestClient()
            .post()
            .uri("/amuse/v1/auth/signout")
            //.header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
            .header("Authorization", "Bearer " + token)
            .bodyValue(request2)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<SignoutResponse>>() {}).returnResult().getResponseBody();
            // .expectBody()
            //    .jsonPath("$.statusCode").isEqualTo(Constan\ts.Status.Code.STATUS_CODE_OK);
        
        logger.info("signout response {}", signoutResponse);


	}
}
