package com.ramcel.cinema.reservation.seat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{
    @Override
    public List<Seat> getAvailableSeats(long screeningId) {
        return null;
    }
}
