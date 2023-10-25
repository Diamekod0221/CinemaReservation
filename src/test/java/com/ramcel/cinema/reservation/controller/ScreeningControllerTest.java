package com.ramcel.cinema.reservation.controller;

import com.ramcel.cinema.reservation.functionalities.screening.Movie;
import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import com.ramcel.cinema.reservation.functionalities.controllers.ScreeningController;
import com.ramcel.cinema.reservation.functionalities.screening.ScreeningService;
import com.ramcel.cinema.reservation.screening.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ScreeningController.class)
public class ScreeningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScreeningService screeningService;

    @Test
    public void return404OnInvalidRequest() throws Exception {
        when(screeningService.findScreenings(LocalDateTime.of(2022,10,19,2,30))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/screening/find-screenings/2022-10-19T02:30"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void return200OnValidRequest() throws Exception {
        LocalDateTime input = LocalDateTime.of(2022,10,19,2,30);
        Screening testScreening = new Screening(1,
                new Movie("bobo", 3600),
                1,
                input
                );

        when(screeningService.findScreenings(input))
                .thenReturn(List.of(testScreening));

        // Act and Assert: Perform an HTTP GET request to the endpoint and expect a 404 status code
        mockMvc.perform(get("/screening/find-screenings/2022-10-19T02:30"))
                .andExpect(status().isOk());
    }
}
