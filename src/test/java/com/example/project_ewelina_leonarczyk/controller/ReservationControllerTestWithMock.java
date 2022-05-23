package com.example.project_ewelina_leonarczyk.controller;

import com.example.project_ewelina_leonarczyk.entities.ObjectForRent;
import com.example.project_ewelina_leonarczyk.entities.Reservation;
import com.example.project_ewelina_leonarczyk.entities.User;
import com.example.project_ewelina_leonarczyk.model.CreateReservation;
import com.example.project_ewelina_leonarczyk.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTestWithMock {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    private ReservationService reservationService;


    @Test
    void responseShouldBeCreated() throws Exception {
        User landlord = new User("landlord");
        User tenant = new User("tenant");
        LocalDate start = LocalDate.parse("2022-07-05");
        LocalDate end = LocalDate.parse("2022-07-20");
        ObjectForRent objectForRent = new ObjectForRent("Name", 20, 40, "Describe", landlord);
        Reservation reservation = new Reservation(start, end, landlord, objectForRent, tenant);
        CreateReservation createReservation = new CreateReservation(reservation.getTenant(),
                reservation.getObjectForRent(), reservation.getStart(), reservation.getEnd());
        String responseJson = objectMapper.writeValueAsString(reservation);
        String json = objectMapper.writeValueAsString(createReservation);

        Mockito.when(reservationService.validationDate(anyInt(), eq(start), eq(end))).thenReturn("OK");

        Mockito.when(reservationService.create(eq(reservation.getTenant()), eq(reservation.getObjectForRent()),
                eq(reservation.getStart()), eq(reservation.getEnd()))).thenReturn(reservation);

        MvcResult result = mvc.perform(post("/reservations").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());

    }

    @Test
    void responseShouldBeBadRequestWithSameDates() throws Exception {
        User landlord = new User("landlord");
        User tenant = new User("tenant");
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        ObjectForRent objectForRent = new ObjectForRent("Name", 20, 40, "Describe", landlord);
        Reservation reservation = new Reservation(start, end, landlord, objectForRent, tenant);
        CreateReservation createReservation = new CreateReservation(reservation.getTenant(),
                reservation.getObjectForRent(), reservation.getStart(), reservation.getEnd());
        String json = objectMapper.writeValueAsString(createReservation);
        String responseJson = "Start date of reservation can not be equal to end date";

        Mockito.when(reservationService.validationDate(anyInt(), eq(start), eq(end))).thenReturn(responseJson);

        MvcResult result = mvc.perform(post("/reservations").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    public void responseShouldBeBadRequestWhenDateOfStartIsAfterEnd() throws Exception {
        User landlord = new User("landlord");
        User tenant = new User("tenant");
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.of(2000, 01, 02);
        ObjectForRent objectForRent = new ObjectForRent("Name", 20, 40, "Describe", landlord);
        Reservation reservation = new Reservation(start, end, landlord, objectForRent, tenant);
        CreateReservation createReservation = new CreateReservation(reservation.getTenant(),
                reservation.getObjectForRent(), reservation.getStart(), reservation.getEnd());
        String json = objectMapper.writeValueAsString(createReservation);
        String responseJson = "Start date of reservations must be before end date";

        Mockito.when(reservationService.validationDate(anyInt(), eq(start), eq(end))).thenReturn(responseJson);

        MvcResult result = mvc.perform(post("/reservations").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

    @Test
    public void responseShouldBeBadRequestWhenDateOfStartIsBeforePresent() throws Exception {
        User landlord = new User("landlord");
        User tenant = new User("tenant");
        LocalDate start = LocalDate.MIN;
        LocalDate end = LocalDate.of(2022, 8, 02);
        ObjectForRent objectForRent = new ObjectForRent("Name", 20, 40, "Describe", landlord);
        Reservation reservation = new Reservation(start, end, landlord, objectForRent, tenant);
        CreateReservation createReservation = new CreateReservation(reservation.getTenant(),
                reservation.getObjectForRent(), reservation.getStart(), reservation.getEnd());
        String json = objectMapper.writeValueAsString(createReservation);
        String responseJson = "Date must be in the future";

        Mockito.when(reservationService.validationDate(anyInt(), eq(start), eq(end))).thenReturn(responseJson);

        MvcResult result = mvc.perform(post("/reservations").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(responseJson, result.getResponse().getContentAsString());
    }

}