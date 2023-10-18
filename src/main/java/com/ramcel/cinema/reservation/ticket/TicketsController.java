package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.reservation.Reservation;
import com.ramcel.cinema.reservation.seat.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    TicketService ticketService;

    @PostMapping(name = "/book-tickets")
    public Reservation bookTickets(@RequestBody List<Ticket> ticketList){
        return ticketService.bookTicket(ticketList);
    }
}


