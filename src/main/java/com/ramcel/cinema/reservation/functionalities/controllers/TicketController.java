package com.ramcel.cinema.reservation.functionalities.controllers;

import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalTicketException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import com.ramcel.cinema.reservation.functionalities.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping(value = "/book-tickets/",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> bookTickets(@Valid @RequestBody List<Ticket> ticketList) throws IllegalReservationException{
         return ResponseEntity.ok(ticketService.bookTicket(ticketList));
    }

    @ExceptionHandler(IllegalReservationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(IllegalReservationException e){
       return ResponseEntity.badRequest().build();
    }

}


