package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.mollik.amuse.amusers.model.request.GenericRequest;
import it.mollik.amuse.amusers.model.response.GenericResponse;
import it.mollik.amuse.amusers.util.HttpUtils;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class AmuseGenericTest {
	
    @Value("${amuse.security.admin:admin}")
	private String admin;

	@Value("${amuse.security.manager01:manager01}")
	private String manager01;

	@Value("${amuse.security.user01:user01}")
	private String user01;
	
	@Value("${amuse.security.defaultpassword:1234}")
	private String defaultPassword;
    
    @Autowired
	private WebTestClient webTestClient;

    @Autowired
	private HttpUtils<GenericRequest> httpUtils;

    @Test
	public void amuseYourself() throws Exception {
        GenericResponse genericResponse = webTestClient
            .get()
            .uri("/api/test/heartbeat")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GenericResponse.class)
            .returnResult().getResponseBody();
        
        assertThat(genericResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
	}


	@Test
    public void jwtAuthorization() throws Exception {
        GenericResponse genericResponse = webTestClient
            .get()
            .uri("/api/test/heartbeat").header("Authorization", httpUtils.getAuthorizazionHeaderValue("user01"))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(GenericResponse.class)
            .returnResult().getResponseBody();
        assertThat(genericResponse.getStatusCode()).isEqualTo(Integer.valueOf(0));
    }

    /**
     * @return String return the admin
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(String admin) {
        this.admin = admin;
    }

    /**
     * @return String return the manager01
     */
    public String getManager01() {
        return manager01;
    }

    /**
     * @param manager01 the manager01 to set
     */
    public void setManager01(String manager01) {
        this.manager01 = manager01;
    }

    /**
     * @return String return the user01
     */
    public String getUser01() {
        return user01;
    }

    /**
     * @param user01 the user01 to set
     */
    public void setUser01(String user01) {
        this.user01 = user01;
    }

    /**
     * @return String return the defaultPassword
     */
    public String getDefaultPassword() {
        return defaultPassword;
    }

    /**
     * @param defaultPassword the defaultPassword to set
     */
    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }


    /**
     * @return HttpUtils return the jwtUtils
     */
    public HttpUtils<GenericRequest> getHttpUtils() {
        return httpUtils;
    }

    /**
     * @param httpUtils the httpUtils to set
     */
    public void setHttpUtils(HttpUtils<GenericRequest> httpUtils) {
        this.httpUtils = httpUtils;
    }

    
	public WebTestClient getWebTestClient() {
        return webTestClient;
    }


    public void setWebTestClient(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

}
