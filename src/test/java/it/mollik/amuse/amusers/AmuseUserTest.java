package it.mollik.amuse.amusers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.Key;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse user tests")
@AutoConfigureRestDocs
public class AmuseUserTest extends AmuseRsApplicationTests {
    
    private Logger logger = LoggerFactory.getLogger(AmuseUserTest.class);

    @Value("${amuse.security.user02:user02}")
    private String user02;

    @Value("${amuse.security.user04:user04}")
    private String user04;

    @Order(1)
    @DisplayName("Users list")
    @ParameterizedTest
    @ValueSource(ints = {7, 8})
	public void listUsers(int results) throws Exception {

        this.getMockMvc()
            .perform(get(Constants.Api.USERS_API + "/list")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .param("pageIndex", Integer.toString(0))
                .param("pageSize", Integer.toString(10))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc(Constants.Api.USERS_API + "/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)));
       
	}

    @Test
    @Order(2)
    @DisplayName("User02 details")
	public void viewUser02() throws Exception {

        this.getMockMvc()
            .perform(get(Constants.Api.USERS_API + "/detail/5")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc(Constants.Api.USERS_API + "/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].username", equalTo(user02)));

	}
    
    @Test
    @Order(3)
    @DisplayName("User04 update")
	public void updateUser04() throws Exception {

        MvcResult detail = this.getMockMvc()
            .perform(get(Constants.Api.USERS_API + "/detail/7")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].username", is(user04)))
        .andReturn();
        AmuseResponse<User> amuseDetailResponse = getObjectMapper().readValue(detail.getResponse().getContentAsString(), new TypeReference<AmuseResponse<User>>() {});

        User u = amuseDetailResponse.getData().get(0);
        logger.info("user {}", u);
        u.setEmail("user04updated@localhost");
        logger.info("updated user {}", u);
        
        AmuseRequest<User> amuseUpdateRequest = new AmuseRequest<>(new Key(getAdmin()), Stream.of(u).collect(Collectors.toList()));
        MvcResult update = this.getMockMvc()
            .perform(post(Constants.Api.USERS_API + "/update/7")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getAdmin(), ERole.ADMIN.getValue()))
                .content(amuseUpdateRequest.toJSONString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andDo(restDoc(Constants.Api.USERS_API + "/update"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                    .andExpect(jsonPath("$.data[0].username", is(user04)))
                    .andExpect(jsonPath("$.data[0].email", is("user04updated@localhost")))
        .andReturn();
        AmuseResponse<User> updateResponse = getObjectMapper().readValue(update.getResponse().getContentAsString(), new TypeReference<AmuseResponse<User>>() {});

        logger.info("updated user {}", updateResponse.toJSONString());
        
    }
    
    // @ParameterizedTest
    // @ValueSource(strings = {"6"})
    // @Order(4)
    // @DisplayName("User03 delete")
    // public void deleteUser03(String userId) throws Exception {         
        
    //     this.getMockMvc()
    //         .perform(delete(Constants.Api.USERS_API + "/delete/{userId}", userId)
    //             .header("Authorization", getHttpUtils().buildAuthHeaderValue(getAdmin(), ERole.ADMIN.getValue()))
    //             .accept(MediaType.APPLICATION_JSON))
    //             .andDo(print())
    //             .andDo(restDoc(Constants.Api.USERS_API + "/delete"))
    //                 .andExpect(status().isOk())
    //                 .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
    //     .andReturn();

    // }

}
