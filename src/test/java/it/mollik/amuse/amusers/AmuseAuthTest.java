package it.mollik.amuse.amusers;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.InetAddress;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.request.LoginRequest;
import it.mollik.amuse.amusers.model.request.SignoutRequest;
import it.mollik.amuse.amusers.model.request.SignupRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;
import it.mollik.amuse.amusers.model.response.SigninResponse;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse base tests")
public class AmuseAuthTest extends AmuseRsApplicationTests{
    
    private Logger logger = LoggerFactory.getLogger(AmuseAuthTest.class);

    /**
     *
     */
    private static final String DEFAULT_PASSWORD = "1234";

	@Test
    @Order(1)
    @DisplayName("Access denied")
	public void accessDenied() throws Exception {
        logger.info("Start accessDenied");
        this.getMockMvc()
            .perform(get("/amuse/v1/test/amuseuser").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnauthorized());
		
	}

	@Test
    @Order(2)
    @DisplayName("Signup")
	public void signup() throws Exception {
        logger.info("Start signup");

		SignupRequest signupRequest = new SignupRequest("testuser", "testuser@localhost", Stream.of("user").collect(Collectors.toList()), DEFAULT_PASSWORD);
		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new Key("testuser"), Stream.of(signupRequest).collect(Collectors.toList()));
		
        this.getMockMvc()
            .perform(post("/amuse/v1/auth/signup")
                .content(request.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("auth/signup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].username", is("testuser")));
	}

	@Test
    @Order(3)
    @DisplayName("Signup error - email already used")
	public void emailAlreadyUsed() throws Exception {
        logger.info("Start emailAlreadyUsed");

		SignupRequest signupRequest = new SignupRequest("user05", "user01@localhost", Stream.of("user").collect(Collectors.toList()), DEFAULT_PASSWORD);
		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new Key("testuser"), Stream.of(signupRequest).collect(Collectors.toList()));

        this.getMockMvc()
            .perform(post("/amuse/v1/auth/signup")
                .content(request.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
                .andExpect(status().isBadRequest());
    }

	@Test
    @Order(4)
    @DisplayName("Signup error - username already used")
	public void usernameAlreadyTaken() throws Exception {
        logger.info("Start usernameAlreadyTaken");

		SignupRequest signupRequest = new SignupRequest(getUser01(), "user05@localhost", Stream.of("user").collect(Collectors.toList()), DEFAULT_PASSWORD);
		AmuseRequest<SignupRequest> request = new AmuseRequest<>(new Key("testuser"), Stream.of(signupRequest).collect(Collectors.toList()));

        this.getMockMvc()
            .perform(post("/amuse/v1/auth/signup")
                .content(request.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
       
    }

    @Test
    @Order(5)
	public void signinError() throws Exception {
        logger.info("Start signinError");

		LoginRequest loginRequest = new LoginRequest(getUser01(), "1235");
		AmuseRequest<LoginRequest> request = new AmuseRequest<>(new Key("user01"), Stream.of(loginRequest).collect(Collectors.toList()));
        this.getMockMvc()
            .perform(post("/amuse/v1/auth/signin")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .content(request.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnauthorized());

	}

    @ParameterizedTest
    @ValueSource(strings = {"user01"})
    @Order(6)
	public void signinAndSignout(String username) throws Exception {
        logger.info("START test signinAndSignout");
        
        logger.info("signin {}", username);

		LoginRequest loginRequest = new LoginRequest(username, DEFAULT_PASSWORD);
		AmuseRequest<LoginRequest> amuseLoginRequest = new AmuseRequest<>(new Key(username), Stream.of(loginRequest).collect(Collectors.toList()));
		MvcResult loginResult = this.getMockMvc()
            .perform(post("/amuse/v1/auth/signin")
                .header("HTTP_CLIENT_IP", InetAddress.getLocalHost().getHostAddress())
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(username, ERole.USER.getValue()))
                .content(amuseLoginRequest.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("auth/signin"))
                .andExpect(status().isOk())
            .andReturn();
        AmuseResponse<SigninResponse> signinResponse = getObjectMapper().readValue(loginResult.getResponse().getContentAsString(), new TypeReference<AmuseResponse<SigninResponse>>() {});

        String token = signinResponse.getData().get(0).getToken();
        logger.info("signin response {}", signinResponse.toJSONString());

        logger.info("signout {}", username);

		SignoutRequest signoutRequest = new SignoutRequest(username);
		AmuseRequest<SignoutRequest> request2 = new AmuseRequest<>(new Key(username), Stream.of(signoutRequest).collect(Collectors.toList()));
        MvcResult signoutResult = this.getMockMvc()
            .perform(post("/amuse/v1/auth/signout")
                .header("HTTP_CLIENT_IP", InetAddress.getLocalHost().getHostAddress())
                .header("Authorization", "Bearer " + token)
                .content(request2.toJSONString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("auth/signout"))
                .andExpect(status().isOk())
            .andReturn();

        logger.info("signout response {}", signoutResult.getResponse().getContentAsString());

	}
}
