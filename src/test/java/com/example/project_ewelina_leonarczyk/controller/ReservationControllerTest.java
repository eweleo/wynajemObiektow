package com.example.project_ewelina_leonarczyk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void shouldReturnReservationsByUser() throws Exception {
        String responseJson = "[{\"id\":2,\"start\":\"2022-05-10\",\"end\":\"2022-05-20\",\"landlord\":\"Anna\",\"objectForRent\":2,\"tenant\":\"Leon\"}]";
        MvcResult result = mvc.perform(post("/reservations/user")
                        .content("{\n" +
                                "    \"name\": \"Leon\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

}