package com.ramcel.cinema.reservation.functionalities.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//todo:add input validation(@valid), exception handling for input
@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping(name = "/get-seats/{id}")
    public ResponseEntity<List<Seat>> getAvailableSeatsByScreening(@PathVariable long screeningId) {
        List<Seat> resultList = seatService.getAvailableSeats(screeningId);
        return getResponse(resultList);
    }

    private <T> ResponseEntity<List<T>> getResponse(List<T> resultList){
        return Optional.of(resultList)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


