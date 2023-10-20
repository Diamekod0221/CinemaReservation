package com.ramcel.cinema.reservation.functionalities.reservation;

import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public record Reservation(List<Ticket>ticketList, java.math.BigDecimal ticketCost, LocalDateTime expirationDate) {}
