package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;

import java.util.List;

public interface TicketService {
    public Reservation bookTicket(Ticket ticket) throws IllegalReservationException;
    public Reservation bookTicket(List<Ticket> ticketList) throws IllegalReservationException;
}
