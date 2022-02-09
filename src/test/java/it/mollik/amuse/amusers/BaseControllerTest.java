package it.mollik.amuse.amusers;



import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import it.mollik.amuse.amusers.controller.BaseController;


@WebMvcTest(BaseController.class)
@AutoConfigureRestDocs
@ActiveProfiles(value = "test")
public class BaseControllerTest {
	
    @Value("${yawapi.basic.auth.user:admin}")
	private String userName;
	
	@Value("${yawapi.basic.auth.password:12345678}")
	private String password;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/").with(csrf()).with(user(this.userName).password(this.password))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Spring is here!")))
				.andDo(document("home"));
	}
}
