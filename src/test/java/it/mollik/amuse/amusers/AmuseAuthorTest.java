package it.mollik.amuse.amusers;


import static org.hamcrest.Matchers.equalTo;
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

import it.mollik.amuse.amusers.config.Constants;
import it.mollik.amuse.amusers.model.ERole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse user tests")
@AutoConfigureRestDocs
public class AmuseAuthorTest extends AmuseGenericTest {

    private Logger logger = LoggerFactory.getLogger(AmuseUserTest.class);

    @Value("${amuse.security.user03:user03}")
    private String user03;

    @Order(1)
    @DisplayName("Author list")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
	public void listAuthors(int results) throws Exception {

        this.getMockMvc()
            .perform(get("/amuse/v1/authors/list")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .param("pageIndex", Integer.toString(0))
                .param("pageSize", Integer.toString(10))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("authors/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)));
       
	}
    
    @Order(2)
    @DisplayName("Author detail")
    @Test
	public void authorDetail() throws Exception {

        this.getMockMvc()
            .perform(get("/amuse/v1/authors/detail/1")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("authors/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].name", equalTo("Italo Calvino")));
                
       
	}
}
