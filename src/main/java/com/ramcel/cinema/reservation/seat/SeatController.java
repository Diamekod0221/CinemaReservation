package com.ramcel.cinema.reservation.seat;

import com.ramcel.cinema.reservation.reservation.Reservation;
import com.ramcel.cinema.reservation.screening.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping(name = "/get-seats/{id}")
    public List<Seat> getAvailableSeatsByScreening(@PathVariable long screeningId) {
        return seatService.getAvailableSeats(screeningId);
    }
}


