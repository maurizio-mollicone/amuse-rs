package it.mollik.amuse.amusers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import it.mollik.amuse.amusers.util.HttpUtils;


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
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
	private TestRestTemplate testRestTemplate;

	@Autowired
	private HttpUtils httpUtils;

    @Test
	public void amuseYourself() throws Exception {
		String body = this.testRestTemplate.getForObject("/api/test/heartbeat", String.class);
		assertThat(body).isEqualTo("aMuse yourself!");
	}


	@Test
    public void jwtAuthorization() throws Exception {
        
        ResponseEntity<String> res = this.testRestTemplate.exchange("/api/test/amuseuser", HttpMethod.GET, httpUtils.buildHeaders(user01), String.class);
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(res.getBody()).startsWith("user01");
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
     * @return TestRestTemplate return the testRestTemplate
     */
    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }

    /**
     * @param testRestTemplate the testRestTemplate to set
     */
    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    /**
     * @return HttpUtils return the jwtUtils
     */
    public HttpUtils getHttpUtils() {
        return httpUtils;
    }

    /**
     * @param httpUtils the httpUtils to set
     */
    public void setHttpUtils(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

}
