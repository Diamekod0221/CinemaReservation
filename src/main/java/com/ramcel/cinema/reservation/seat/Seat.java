package com.ramcel.cinema.reservation.seat;

import com.ramcel.cinema.reservation.screening.Room;
import com.ramcel.cinema.reservation.screening.RoomRow;
import com.ramcel.cinema.reservation.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
@Builder
public class Seat {

    private final long seatId;

    private final long roomId;

    private final long roomRowId;

    private final int seatNumber;

    private final long screeningId;

    private boolean isOccupied;

    private SeatStatus status;

}
