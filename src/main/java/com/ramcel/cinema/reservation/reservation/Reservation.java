package com.ramcel.cinema.reservation.reservation;

import com.ramcel.cinema.reservation.ticket.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public record Reservation(List<Ticket>ticketList, int ticketCost, LocalDateTime expirationDate) {}
