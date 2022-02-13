package it.mollik.amuse.amusers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class AmuseUserTest extends AmuseGenericTest {
    
    @Value("${amuse.security.user03:user03}")
    private String user03;

    @Test
	public void listUsers() throws Exception {
        getWebTestClient()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/amuse/v1/users/list")
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK)
                .jsonPath("$.searchParams.totalItems").isEqualTo(8);
	}

    
    @Test
	public void viewUser03() throws Exception {
        getWebTestClient()
            .get()
            .uri("/amuse/v1/users/detail/6")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
                .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK)
                .jsonPath("$.data[0].userName").isEqualTo(user03);
            
	}
}
