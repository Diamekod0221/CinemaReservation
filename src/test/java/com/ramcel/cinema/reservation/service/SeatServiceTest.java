package com.ramcel.cinema.reservation.service;

import com.ramcel.cinema.reservation.db.entity.MovieEntity;
import com.ramcel.cinema.reservation.db.entity.RoomEntity;
import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.ScreeningRepository;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import com.ramcel.cinema.reservation.functionalities.screening.ScreeningServiceImpl;
import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatService;
import com.ramcel.cinema.reservation.functionalities.seat.SeatServiceImpl;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SeatServiceTest {


    @Autowired
    private SeatService seatService;

    private SeatRepository seatRepository;

    @Mock
    private SeatEntity testSeat;

    @BeforeEach
    public void setup(){

        testSeat.setId(1L);
        testSeat.setStatus(SeatStatus.AVAILABLE);
        testSeat.setSeatNumber(1);

    }

    @Test
    public void findValidSeatMock(){
        when(seatRepository.findSeatByScreening(1L))
                .thenReturn(List.of(testSeat));

        when(testSeat.mapToSeat()).thenReturn(
                Seat.builder()
                        .seatId(1) // You can set appropriate values here
                        .roomId(2)
                        .roomRowId(3)
                        .seatNumber(4)
                        .screeningId(5)
                        .isOccupied(false)
                        .status(SeatStatus.AVAILABLE)
                        .build()
        );

        List<Seat> screeningList = seatService.getAvailableSeats(1L);

        assertEquals(List.of(testSeat.mapToSeat()), screeningList);
    }

}
