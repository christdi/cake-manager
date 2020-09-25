package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.CakeModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CakeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GIVEN_the_retrieve_all_cakes_endpoint_WHEN_called_THEN_return_all_cakes() throws Exception {
        this.mockMvc.perform(get("/cakes"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void GIVEN_the_create_cake_endpoint_WHEN_called_THEN_return_created_status() throws Exception {
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
}
