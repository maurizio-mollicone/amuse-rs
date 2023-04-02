package it.mollik.amuse.amusers;


import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
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
import it.mollik.amuse.amusers.model.orm.Author;
import it.mollik.amuse.amusers.model.request.AmuseRequest;
import it.mollik.amuse.amusers.model.response.AmuseResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("aMuse user tests")
@AutoConfigureRestDocs
public class AmuseAuthorTest extends AmuseGenericTest {

    private Logger logger = LoggerFactory.getLogger(AmuseAuthorTest.class);

    @Value("${amuse.security.user03:user03}")
    private String user03;

    @Order(1)
    @DisplayName("Author list")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
	public void listAuthors(int results) throws Exception {
        logger.info("Author list");
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

    @Order(3)
    @DisplayName("Find By Name")
    @Test
	public void findByName() throws Exception {

        this.getMockMvc()
            .perform(get("/amuse/v1/authors/find")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .param("name", "Italo%20Calvino")
                .param("pageIndex", Integer.toString(0))
                .param("pageSize", Integer.toString(10))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andDo(restDoc("authors/find"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].name", equalTo("Italo Calvino")));
	}


    @Order(4)
    @DisplayName("Create Author")
    @Test
	public void createAuthor() throws Exception {

        Author author = new Author("Michail", "Bulgakov");
        author.setBirthDate(new Date(java.sql.Date.valueOf(LocalDate.of(1891, 5, 15)).getTime()));
        author.setDeathDate(new Date(java.sql.Date.valueOf(LocalDate.of(1940, 3, 10)).getTime()));
        String bio = "Bulgakov nacque a Kiev, al secolo capoluogo dell'allora omonimo governatorato russo (oggi capitale dell'Ucraina), il 15 maggio (il 3 maggio secondo l'allora vigente calendario giuliano) del 1891 da un'agiata famiglia russa foziana[1], primogenito dei sette figli (quattro femmine e due maschi, che poi si sarebbero stabiliti tutti in Francia, a Parigi) di Afanasij Ivanovič Bulgakov, docente universitario di storia e critica delle religioni occidentali presso l'Accademia Teologica di Kiev, oltreché traduttore di testi religiosi, deceduto nel 1906, e di Varvara Michajlovna Pokrovskaja, entrambi originari dell'allora governatorato di Orël (la madre era infatti nata, per l'esattezza, nella città di Karačev), ovvero l'odierno oblast' di Brjansk[2][3]. Cresciuto con un'educazione strettamente religiosa, si legge nei diari della sorella Nadežda di come Miša, diminuitivo con cui era spesso chiamato in famiglia l'autore, abbia da giovane abbandonato la pratica religiosa: giunse infatti egli stesso a dichiararsi agnostico nel 1910, dopo essersi iscritto alla facoltà di medicina a Kiev.";

        author.setBiography(bio.getBytes());

        AmuseRequest<Author> authorRequest = new AmuseRequest<>(new Key("admin"), Stream.of(author).collect(Collectors.toList())); 
        this.getMockMvc()
            .perform(post("/amuse/v1/authors/create")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getAdmin(), ERole.ADMIN.getValue()))
                .content(authorRequest.toJSONString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andDo(restDoc("authors/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].name", equalTo("MichailBulgakov")))
                .andExpect(jsonPath("$.data[0].firstName", equalTo("Michail")))
                .andExpect(jsonPath("$.data[0].lastName", equalTo("Bulgakov")));

    }

    @Order(5)
    @DisplayName("Update Author")
    @Test
	public void updateAuthor() throws Exception {


        MvcResult detailResult = this.getMockMvc()
            .perform(get("/amuse/v1/authors/detail/1")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getUser01(), ERole.USER.getValue()))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(restDoc("authors/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].name", equalTo("Italo Calvino"))).andReturn();

        AmuseResponse<Author> detailResponse = getObjectMapper().readValue(detailResult.getResponse().getContentAsString(), new TypeReference<AmuseResponse<Author>>() {});

                
        Author author = detailResponse.getData().get(0);
        String bio = "Intellettuale di grande impegno politico, civile e culturale, è stato uno dei narratori italiani più importanti del secondo Novecento. Ha seguito molte delle principali tendenze letterarie a lui coeve, dal Neorealismo al Postmoderno, ma tenendo sempre una certa distanza da esse e svolgendo un percorso di ricerca personale e coerente.";
        author.setBiography(bio.getBytes());

        AmuseRequest<Author> authorRequest = new AmuseRequest<>(new Key("admin"), Stream.of(author).collect(Collectors.toList())); 
        this.getMockMvc()
            .perform(post("/amuse/v1/authors/update/1")
                .header("Authorization", getHttpUtils().buildAuthHeaderValue(getAdmin(), ERole.ADMIN.getValue()))
                .content(authorRequest.toJSONString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andDo(restDoc("authors/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                .andExpect(jsonPath("$.data[0].name", equalTo("Italo Calvino")))
                .andExpect(jsonPath("$.data[0].firstName", equalTo("Italo")))
                .andExpect(jsonPath("$.data[0].lastName", equalTo("Calvino")));

    }


    // @Order(6)
    // @DisplayName("Delete Author")
    // @Test
	// public void deleteAuthor() throws Exception {


    //     this.getMockMvc()
    //         .perform(delete("/amuse/v1/authors/delete/1")
    //             .header("Authorization", getHttpUtils().buildAuthHeaderValue(getAdmin(), ERole.ADMIN.getValue()))
    //             .accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andDo(print())
    //         .andDo(restDoc("authors/create"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.statusCode", equalTo(Constants.Status.Code.STATUS_CODE_OK)))
                

    // }
}
