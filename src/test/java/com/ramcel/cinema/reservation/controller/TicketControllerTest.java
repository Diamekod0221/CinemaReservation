package com.ramcel.cinema.reservation.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramcel.cinema.reservation.functionalities.controllers.TicketController;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketService;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private TicketService ticketService;

    @Test
    public void testValidBookTickets() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();

        Ticket validTicket = Ticket.builder()
                .name("John")
                .surname("Doe")
                .screeningId(1)
                .screeningDate(LocalDateTime.now())
                .seatId(101)
                .type(TicketType.ADULT)
                .build();
        ticketList.add(validTicket);


        when(ticketService.bookTicket(ticketList)).thenReturn(createMockReservation(validTicket));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(ticketList);

        mockMvc.perform(post("/tickets/book-tickets")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    private Reservation createMockReservation(Ticket ticket) {
        return new Reservation(List.of(ticket),
                ticket.getType().getPrice(),
                LocalDateTime.now().plusDays(1)
        );
    }

    @Test
    public void testInvalidBookTickets() throws Exception {
        String invalidJsonRequest = "{\"invalidField\": \"Invalid Value\"}";

        mockMvc.perform(post("/tickets/book-tickets")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(invalidJsonRequest))
                .andExpect(status().isBadRequest());
    }


}
