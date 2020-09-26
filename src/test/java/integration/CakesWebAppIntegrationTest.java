package integration;

import com.waracle.cakemgr.CakeManagerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CakeManagerApplication.class)
@TestPropertySource(properties = {"github.client.id = foo;github.client.secret = bar"})
public class CakesWebAppIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void GIVEN_the_view_page_of_the_webapp_WHEN_retrieved_THEN_page_returned_okay() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(content().string(containsString("Glorious Cakes!")))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    public void GIVEN_the_add_page_of_the_webapp_WHEN_retrieved_THEN_page_returned_okay() throws Exception {
        this.mockMvc.perform(get("/add"))
                .andDo(print())
                .andExpect(content().string(containsString("Add a new cake")))
                .andExpect(status().isOk());
    }

    @WithMockUser("spring")
    @Test
    public void GIVEN_the_add_post_endpoint_WHEN_invalid_data_supplied_THEN_validation_errors_returned() throws Exception {
        this.mockMvc.perform(post("/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }

    @WithMockUser("spring")
    @Test
    public void GIVEN_the_add_post_endpoint_WHEN_duplicate_title_supplied_THEN_validation_error_returned() throws Exception {
        this.mockMvc.perform(post("/add")
                .param("title", "title")
                .param("desc", "desc")
                .param("image", "image"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        this.mockMvc.perform(post("/add")
                .param("title", "title")
                .param("desc", "desc")
                .param("image", "image"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    public void GIVEN_an_unauthenticated_user_WHEN_attempting_to_access_web_application_THEN_redirected_to_login() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
