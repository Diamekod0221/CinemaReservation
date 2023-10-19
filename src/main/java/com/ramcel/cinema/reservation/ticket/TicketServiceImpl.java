package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.reservation.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    @Override
    public Reservation bookTicket(Ticket ticket) {
        return null;
    }

    @Override
    public Reservation bookTicket(List<Ticket> ticketList) {
        return null;
    }
}
