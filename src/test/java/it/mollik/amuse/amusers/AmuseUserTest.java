package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.model.orm.User;
import it.mollik.amuse.amusers.model.response.AmuseResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class AmuseUserTest extends AmuseGenericTest {
    
    @Value("${amuse.security.user03:user03}")
    private String user03;

    @Test
	public void listUsers() throws Exception {
        AmuseResponse<User> response = getWebTestClient()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/amuse/v1/users/list")
                .queryParam("pageIndex", 0)
                .queryParam("pageSize", 10)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<User>>(){})
            .returnResult().getResponseBody();
        
        assertThat(response.getStatusCode()).isEqualTo(Integer.valueOf(0));
	}

    
    @Test
	public void viewUser03() throws Exception {
        AmuseResponse<User> response = getWebTestClient()
            .get()
            .uri("/amuse/v1/users/detail/6")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01(), ERole.USER.getValue()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<AmuseResponse<User>>(){})
            .returnResult().getResponseBody();
        
        assertThat(response.getStatusCode()).isEqualTo(Integer.valueOf(0));
        assertThat(response.getData().get(0).getName()).isEqualTo(user03);

	}
}
