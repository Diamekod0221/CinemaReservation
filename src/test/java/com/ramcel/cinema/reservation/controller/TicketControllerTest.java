package com.ramcel.cinema.reservation.controller;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @Test
    public void testValidBookTickets() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();

        // Create valid Ticket objects and add them to the list
        Ticket validTicket1 = Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(1)
                .screeningDate(LocalDateTime.now())
                .seatId(101)
                .type(TicketType.ADULT)
                .build();
        ticketList.add(validTicket1);

        Ticket validTicket2 = Ticket.builder()
                .name("Jane")
                .surname("Smith")
                .screeningId(2)
                .screeningDate(LocalDateTime.now())
                .seatId(102)
                .type(TicketType.CHILD)
                .build();
        ticketList.add(validTicket2);

        // Mock the behavior of the TicketService to return a valid Reservation
        when(ticketService.bookTicket(any(List.class))).thenReturn(createValidReservation());

        String jsonRequest = objectMapper.writeValueAsString(ticketList);

        mockMvc.perform(post("/tickets/book-tickets/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidBookTickets() throws Exception {
        List<Ticket> invalidTicketList = new ArrayList<>();

        // Create invalid Ticket objects and add them to the list
        // For example, omitting required fields or using incorrect data
        Ticket invalidTicket1 = Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(1)
                .seatId(101)  // Omitting the screeningDate field
                .type(TicketType.ADULT)
                .build();
        invalidTicketList.add(invalidTicket1);

        // Mock the behavior of the TicketService to throw an IllegalReservationException
        when(ticketService.bookTicket(any(List.class))).thenThrow(IllegalReservationException.class);

        String jsonRequest = objectMapper.writeValueAsString(invalidTicketList);

        mockMvc.perform(post("/tickets/book-tickets/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    // Create a valid Reservation for testing purposes
    private Reservation createValidReservation() {return new Reservation(Collections.emptyList(), BigDecimal.ONE, LocalDateTime.now());
    }
}
