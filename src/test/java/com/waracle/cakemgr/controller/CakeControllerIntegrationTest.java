package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static com.waracle.cakemgr.model.builder.CakeModelBuilder.aCakeModel;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(MockitoJUnitRunner.class)
public class CakeControllerIntegrationTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CakeController cakeController;

    @Mock
    private CakeRepository cakeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(cakeController).build();
    }

    @Test
    public void GIVEN_the_retrieve_all_cakes_endpoint_WHEN_called_THEN_return_all_cakes() throws Exception {
        when(cakeRepository.findAll()).thenReturn(Arrays.asList(
                aCakeModel()
                        .withId(UUID.randomUUID())
                        .withTitle("Tasty cake")
                        .withDesc("A tasty cake")
                        .withImage("http://wwww.cakesrus.com/tasty.jpg")
                        .build(),
                aCakeModel()
                        .withId(UUID.randomUUID())
                        .withTitle("Yummy cake")
                        .withDesc("A yummy cake")
                        .withImage("http://www.cakesrus.com/yummy.jpg")
                        .build()));

        this.mockMvc.perform(get("/cakes"))
                .andDo(print())
                .andExpect(content().string(containsString("tasty")))
                .andExpect(content().string(containsString("yummy")))
                .andExpect(status().isOk());
    }

    @Test
    public void GIVEN_the_create_cake_endpoint_WHEN_called_with_valid_cake_THEN_return_created_status() throws Exception {
        CakeModel cake = new CakeModel();
        cake.setTitle("Tasty cake");
        cake.setDesc("A tasty cake");
        cake.setImage("http://wwww.cakesrus.com/tasty.jpg");

        this.mockMvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(cake)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void GIVEN_the_create_cake_endpoint_WHEN_called_with_null_cake_THEN_return_error_status() throws Exception {
        this.mockMvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GIVEN_the_create_cake_endpoint_WHEN_called_with_malformed_cake_THEN_return_error_status() throws Exception {
        this.mockMvc.perform(post("/cakes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{'bad': 'bad'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
