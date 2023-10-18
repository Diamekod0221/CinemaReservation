package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.db.ScreeningEntity;
import com.ramcel.cinema.reservation.db.SeatEntity;
import com.ramcel.cinema.reservation.screening.Screening;
import com.ramcel.cinema.reservation.seat.Seat;

import java.time.LocalDateTime;

public class Ticket {
    private String name;

    private String surname;

    private long screeningId;

    private LocalDateTime screeningDate;

    private int seatId;

    private TicketType type;
}
