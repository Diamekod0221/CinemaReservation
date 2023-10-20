package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//todo:add input validation(@valid)
@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    TicketService ticketService;

    @PostMapping(name = "/book-tickets")
    public ResponseEntity<Reservation> bookTickets(@RequestBody List<Ticket> ticketList){
        try{ return ResponseEntity.ok(ticketService.bookTicket(ticketList));}
        catch (InvalidSeatException e){
            return ResponseEntity.badRequest().build();
        }
    }
}


