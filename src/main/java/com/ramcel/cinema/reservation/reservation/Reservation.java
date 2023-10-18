package com.ramcel.cinema.reservation.reservation;

import com.ramcel.cinema.reservation.ticket.Ticket;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
public class Reservation {

    private List<Ticket> reservedTickets;

    private long ticketCost;

    private LocalDateTime reservationExpiration;
}
