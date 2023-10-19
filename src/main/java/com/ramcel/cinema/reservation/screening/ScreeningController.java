package com.ramcel.cinema.reservation.screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/screening")
public class ScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @GetMapping(value = "/find-screenings/{date}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Screening> findScreenings(@PathVariable("date") LocalDateTime dateTime){
        return screeningService.findScreenings(dateTime);
    }

    @GetMapping(value = "/find-screenings/{movie}/{date}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Screening> findScreenings(@PathVariable("movie") Movie movie, @PathVariable("date") LocalDateTime date){
        return screeningService.findScreenings(movie, date);
    }



}
