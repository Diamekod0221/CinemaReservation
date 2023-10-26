package com.ramcel.cinema.reservation.functionalities.controllers;

import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping(name = "/book-tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> bookTickets(@Valid @RequestBody List<Ticket> ticketList) throws IllegalReservationException{
         return ResponseEntity.ok(ticketService.bookTicket(ticketList));
    }

}

