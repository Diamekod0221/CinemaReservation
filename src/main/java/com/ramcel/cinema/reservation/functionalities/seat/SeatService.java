package com.ramcel.cinema.reservation.functionalities.seat;

import java.util.List;

public interface SeatService {
    public List<Seat> getAvailableSeats(Long screeningId);
}
