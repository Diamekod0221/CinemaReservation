package com.ramcel.cinema.reservation.functionalities.controllers;

import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/get-seats/{screeningId}")
    public ResponseEntity<List<Seat>> getAvailableSeatsByScreening(@Valid @PathVariable long screeningId) {
        List<Seat> resultList = seatService.getAvailableSeats(screeningId);
        return getResponse(resultList);
    }

    private <T> ResponseEntity<List<T>> getResponse(List<T> resultList){
        return Optional.of(resultList)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}


