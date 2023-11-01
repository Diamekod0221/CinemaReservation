package com.ramcel.cinema.reservation.controller;

import com.ramcel.cinema.reservation.functionalities.controllers.SeatController;
import com.ramcel.cinema.reservation.functionalities.screening.Movie;
import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import com.ramcel.cinema.reservation.functionalities.screening.ScreeningService;
import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatService;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatController.class)
public class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService seatService;

    @Test
    public void return204OnFullScreening() throws Exception {
        when(seatService.getAvailableSeats(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/seat/get-seats/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void return200OnValidRequest() throws Exception {
        long input = 1L;
        Seat testSeat = new Seat(
                1,
                1,
                1,
                1,
                1,
                false,
                SeatStatus.AVAILABLE
        );

        when(seatService.getAvailableSeats(input))
                .thenReturn(List.of(testSeat));

        mockMvc.perform(get("/seat/get-seats/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void handleInvalidInput() throws Exception{

        mockMvc.perform(get("/seat/get-seats/absdf"))
                .andExpect(status().isBadRequest());

    }
}




