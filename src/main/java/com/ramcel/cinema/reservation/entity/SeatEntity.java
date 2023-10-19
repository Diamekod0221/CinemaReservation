package com.ramcel.cinema.reservation.entity;

import com.ramcel.cinema.reservation.seat.Seat;
import com.ramcel.cinema.reservation.seat.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "seats")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SeatEntity extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "room_row_id")
    private RoomRowEntity roomRow;

    private int seatNumber;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;

    private boolean isOccupied;

    private SeatStatus status;

    public Seat mapToSeat(){
        return Seat.builder()
                .seatId(super.getId())
                .roomId(room.getId())
                .roomRowId(roomRow.getId())
                .seatNumber(seatNumber)
                .screeningId(screening.getId())
                .isOccupied(isOccupied)
                .status(status)
                .build();
    }
}
