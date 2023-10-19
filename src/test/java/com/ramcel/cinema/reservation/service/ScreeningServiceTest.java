package com.ramcel.cinema.reservation.service;

import com.ramcel.cinema.reservation.db.MovieEntity;
import com.ramcel.cinema.reservation.db.RoomEntity;
import com.ramcel.cinema.reservation.db.ScreeningEntity;
import com.ramcel.cinema.reservation.db.ScreeningRepository;
import com.ramcel.cinema.reservation.screening.Movie;
import com.ramcel.cinema.reservation.screening.Screening;
import com.ramcel.cinema.reservation.screening.ScreeningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@Sql(scripts = {"/import_movies.sql", "/import_rooms.sql", "/import_screenings.sql"})
public class ScreeningServiceTest{

    @Autowired
    private ScreeningServiceImpl screeningService;

    @MockBean
    private ScreeningRepository screeningRepository;

    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
    private ScreeningEntity testEntity;



    @BeforeEach
    public void setup(){
        startPeriod = LocalDateTime.of(2023,10,19,0,30);
        endPeriod =  LocalDateTime.of(2023,10,19,5,0);


        testEntity = new ScreeningEntity(
                new MovieEntity("Star Wars", 3600),
                new RoomEntity(),
                endPeriod.minusMinutes(45)
        );
    }

    @Test
    public void findValidScreeningMock(){
        LocalDateTime input = LocalDateTime.of(2023, 10,19, 1, 0);

        when(screeningRepository.findScreeningsInPeriod(startPeriod,endPeriod))
                .thenReturn(List.of(testEntity));

        List<Screening> screeningList = screeningService.findScreenings(input);

        assertEquals(List.of(testEntity.mapToScreening()), screeningList);
        verify(this.screeningRepository).findScreeningsInPeriod(startPeriod,endPeriod);
    }

    @Test
    public void findValidMovieScreeningsMock(){
        LocalDateTime input = LocalDateTime.of(2023, 10,19, 1, 0);

        when(screeningRepository.findScreeningsInPeriod(startPeriod,endPeriod))
                .thenReturn(List.of(testEntity));

        Movie testMovie = new Movie("Star Wars", 3600);

        List<Screening> screeningList = screeningService.findScreenings(testMovie, input);


        assertEquals(List.of(testEntity.mapToScreening()), screeningList);
    }

    @Test
    public void findValidScreeningDB(){
        LocalDateTime input = LocalDateTime.of(2023, 10,10, 3, 0);

        List<Screening> actual = screeningService.findScreenings(input);

        Screening expectedScreening1 = new Screening(1,
                new Movie("Star Wars", 3600),
                1,
                LocalDateTime.of(2023,10,10, 4,15));

        Screening expectedScreening2 = new Screening(2,
                new Movie("Captain Hook", 5400),
                2,
                LocalDateTime.of(2023,10,10, 6,15));

        assertEquals(List.of(expectedScreening1,expectedScreening2), actual);

    }


}
