package com.ramcel.cinema.reservation.screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/screening")
public class ScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @GetMapping(value = "/find-screenings/{date}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Screening>> findScreenings(@PathVariable("date") LocalDateTime dateTime){
        List<Screening> resultList = screeningService.findScreenings(dateTime);

        return checkResponse(resultList);
    }

    @GetMapping(value = "/find-screenings/{movie}/{date}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Screening>> findScreenings(@PathVariable("movie") Movie movie, @PathVariable("date") LocalDateTime date){
        List<Screening> resultList = screeningService.findScreenings(movie, date);

        return checkResponse(resultList);
    }

    private ResponseEntity<List<Screening>> checkResponse(List<Screening> resultList){
        return Optional.of(resultList)
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




}
