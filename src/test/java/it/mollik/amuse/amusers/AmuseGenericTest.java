package it.mollik.amuse.amusers;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import it.mollik.amuse.amusers.model.ERole;
import it.mollik.amuse.amusers.service.impl.HelperService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse base tests")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
// @AutoConfigureMockWebServiceClient
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
	private MockMvc mockMvc;

    @Autowired
	private HelperService httpUtils;

	@Autowired
    ObjectMapper objectMapper;
    
    @BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
			.apply(documentationConfiguration(restDocumentation)) 
			.build();
            
	}
    
    @Test
    @Order(1)
    @DisplayName("HeartBeat public access")
	public void heartbeat() throws Exception {
        this.mockMvc
            .perform(get("/amuse/v1/test/heartbeat").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode", is(0)));
        // webTestClient
        //     .get()
        //     .uri("/amuse/v1/test/heartbeat")
        //     .accept(MediaType.APPLICATION_JSON)
        //     .exchange()
        //     .expectStatus()
        //     .isOk()
        //     .expectBody()
        //         .jsonPath("$.statusCode").isEqualTo(Constants.Status.Code.STATUS_CODE_OK).consumeWith(document("heartbeat"));
        
	}


	@Test
    @Order(2)
    @DisplayName("HeartBeat authenticated access")
    public void auth() throws Exception {
        this.mockMvc
            .perform(
                get("/amuse/v1/test/heartbeat")
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", getHttpUtils().buildAuthHeaderValue("user01", ERole.USER.getValue())))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode", is(0)));
        
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
    public HelperService getHttpUtils() {
        return httpUtils;
    }

    /**
     * @param httpUtils the httpUtils to set
     */
    public void setHttpUtils(HelperService httpUtils) {
        this.httpUtils = httpUtils;
    }

    
    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


}
