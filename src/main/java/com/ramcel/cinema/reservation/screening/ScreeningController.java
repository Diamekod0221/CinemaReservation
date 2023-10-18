package com.ramcel.cinema.reservation.screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/screening")
public class ScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @GetMapping(value = "/find-screenings/{date}/{timestamp}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String findScreenings(@PathVariable("date") LocalDate date, @PathVariable("timestamp") LocalTime time){

    }



}
