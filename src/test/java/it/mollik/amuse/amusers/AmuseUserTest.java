package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.model.response.UserResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class AmuseUserTest extends AmuseGenericTest {
    
    @Test
	public void listUsers() throws Exception {
    UserResponse userResponse = getWebTestClient()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/amuse/v1/users/list").queryParam("name", getUser01()).build())
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(UserResponse.class)
            .returnResult().getResponseBody();
        
        assertThat(userResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
	}
    @Value("${amuse.security.user03:user03}")
    private String user03;
    @Test
	public void viewUser03() throws Exception {
    UserResponse userResponse = getWebTestClient()
            .get()
            .uri("/amuse/v1/users/detail/6")
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", getHttpUtils().getAuthorizazionHeaderValue(getUser01()))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(UserResponse.class)
            .returnResult().getResponseBody();
        
        assertThat(userResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
        assertThat(userResponse.getUsers().get(0).getName()).isEqualTo(user03);

	}
}
