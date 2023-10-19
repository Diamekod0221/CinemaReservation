package com.ramcel.cinema.reservation.seat;

import com.ramcel.cinema.reservation.entity.SeatEntity;
import com.ramcel.cinema.reservation.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{

    @Autowired
    private SeatRepository seatRepository;
    @Override
    public List<Seat> getAvailableSeats(long screeningId) {
        return seatRepository.findSeatByScreening(screeningId).stream().map(SeatEntity::mapToSeat).toList();
    }
}
