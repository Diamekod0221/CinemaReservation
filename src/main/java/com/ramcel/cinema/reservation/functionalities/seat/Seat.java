package com.ramcel.cinema.reservation.functionalities.seat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
