package com.ramcel.cinema.reservation.service.integration;


import com.ramcel.cinema.reservation.db.repositories.ScreeningRepository;
import com.ramcel.cinema.reservation.functionalities.screening.Movie;
import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import com.ramcel.cinema.reservation.functionalities.screening.ScreeningServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/import_movies.sql", "/import_rooms.sql", "/import_screenings.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/delete_test_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ScreeningServiceIntegrationTest {


    @Autowired
    private ScreeningServiceImpl screeningService;

    @Autowired
    private ScreeningRepository repository;

    @Test
    public void findValidScreeningDBDate(){
        LocalDateTime input = LocalDateTime.of(2023, 11,2, 2, 0);

        List<Screening> actual = screeningService.findScreenings(input);

        Screening expectedScreening1 = new Screening(1,
                new Movie("Star Wars", 3600),
                1,
                LocalDateTime.of(2023,11,2, 4,15));

        Screening expectedScreening2 = new Screening(7,
                new Movie("Captain Hook", 5400),
                3,
                LocalDateTime.of(2023,11,2, 4,15));

        assertEquals(List.of(expectedScreening2,expectedScreening1), actual);

    }

    @Test
    public void findValidScreeningDBDateMovie(){
        LocalDateTime input = LocalDateTime.of(2023, 11,2, 2, 0);
        Movie testMovie = new Movie("Star Wars", 3600);

        List<Screening> actual = screeningService.findScreenings(testMovie, input);

        Screening expectedScreening1 = new Screening(1,
                testMovie,
                1,
                LocalDateTime.of(2023,11,2, 4,15));

        assertEquals(List.of(expectedScreening1), actual);

    }

    @Test
    public void findInvalidScreeningDB(){
        LocalDateTime input = LocalDateTime.of(2022, 10,10, 3, 0);

        List<Screening> actual = screeningService.findScreenings(input);

        assertEquals(List.of(), actual);
    }

}
