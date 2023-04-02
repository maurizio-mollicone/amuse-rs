package it.mollik.amuse.amusers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.mollik.amuse.amusers.model.ERole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse base tests")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class AmuseGenericTest extends AmuseRsApplicationTests {
	
    @Test
    @Order(1)
    @DisplayName("HeartBeat public access")
	public void heartbeat() throws Exception {
        this.getMockMvc()
            .perform(get("/amuse/test/heartbeat")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("test/heartbeat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(0)));
       
	}


	@Test
    @Order(2)
    @DisplayName("HeartBeat authenticated access")
    public void auth() throws Exception {
        this.getMockMvc()
            .perform(get("/amuse/test/heartbeat")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getHttpUtils().buildAuthHeaderValue("user01", ERole.USER.getValue())))
            .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(0)));
        
    }

}
