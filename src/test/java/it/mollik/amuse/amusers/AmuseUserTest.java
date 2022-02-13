package it.mollik.amuse.amusers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.config.EmbeddedRedisTestConfiguration;
import it.mollik.amuse.amusers.model.ERole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmbeddedRedisTestConfiguration.class)
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse user tests")
public class AmuseUserTest extends AmuseGenericTest {
    
    @Value("${amuse.security.user03:user03}")
    private String user03;

    @Order(1)
    @DisplayName("Users list")
    @ParameterizedTest
    @ValueSource(ints = {7, 8})
	public void listUsers(int results) throws Exception {
        getWebTestClient()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/amuse/v1/users/list")
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK);
	}

    
    @Test
    @Order(2)
    @DisplayName("User03 details")
	public void viewUser03() throws Exception {
        getWebTestClient()
            .get()
            .uri("/amuse/v1/users/detail/6")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK)
                .jsonPath("$.data[0].userName").isEqualTo(user03);
            
	}
}
