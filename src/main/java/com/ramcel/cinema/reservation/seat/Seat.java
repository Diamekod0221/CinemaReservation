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

    private final Room room;

    private final RoomRow roomRow;

    private final int seatNumber;

    private final Screening screening;

    private boolean isOccupied;

    private SeatStatus status;

}
