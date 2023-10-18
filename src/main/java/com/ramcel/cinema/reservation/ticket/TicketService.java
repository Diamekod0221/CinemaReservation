package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.reservation.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    public Reservation bookTicket(Ticket ticket);
    public Reservation bookTicket(List<Ticket> ticketList);
}
