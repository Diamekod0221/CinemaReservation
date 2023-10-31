package com.ramcel.cinema.reservation.service.unit;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatService;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SeatServiceTest {


    @Autowired
    private SeatService seatService;

    @MockBean
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
        when(seatRepository.findSeatByScreeningId(1L))
                .thenReturn(List.of(testSeat));

        when(testSeat.mapToSeat()).thenReturn(
                Seat.builder()
                        .seatId(1)
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
