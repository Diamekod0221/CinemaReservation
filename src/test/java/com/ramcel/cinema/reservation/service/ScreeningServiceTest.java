package com.ramcel.cinema.reservation.service;

import com.ramcel.cinema.reservation.db.entity.MovieEntity;
import com.ramcel.cinema.reservation.db.entity.RoomEntity;
import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.db.repositories.ScreeningRepository;
import com.ramcel.cinema.reservation.functionalities.screening.Movie;
import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import com.ramcel.cinema.reservation.functionalities.screening.ScreeningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
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

        RoomEntity mockRoom = new RoomEntity();
        mockRoom.setId(1L);
        mockRoom.setNumberOfRows(10);


        testEntity = new ScreeningEntity(
                new MovieEntity("Star Wars", 3600),
                mockRoom,
                endPeriod.minusMinutes(45)
        );
        testEntity.setId(1L);
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
    public void findValidMovieScreeningsMockByMovie(){
        LocalDateTime input = LocalDateTime.of(2023, 10,19, 1, 0);

        Movie testMovie = new Movie("Star Wars", 3600);

        when(screeningRepository.findScreeningsInPeriod(startPeriod,endPeriod))
                .thenReturn(List.of(testEntity));

        List<Screening> screeningList = screeningService.findScreenings(testMovie, input);

        assertEquals(List.of(testEntity.mapToScreening()), screeningList);
        verify(this.screeningRepository).findScreeningsInPeriod(startPeriod,endPeriod);
    }

    @Test
    public void findInvalidScreeningsMock(){

        LocalDateTime input = LocalDateTime.of(2022, 10,19, 1, 0);

        when(screeningRepository.findScreeningsInPeriod(startPeriod.minusYears(1),endPeriod.minusYears(1)))
                .thenReturn(List.of());

        List<Screening> screeningList = screeningService.findScreenings(input);

        assertEquals(List.of(), screeningList);
    }


}
