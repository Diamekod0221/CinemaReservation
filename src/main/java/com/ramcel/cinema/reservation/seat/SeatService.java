package com.ramcel.cinema.reservation.seat;

import java.util.List;

public interface SeatService {
    public List<Seat> getAvailableSeats(long screeningId);
}
