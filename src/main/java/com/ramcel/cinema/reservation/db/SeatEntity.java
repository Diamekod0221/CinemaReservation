package com.ramcel.cinema.reservation.db;

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
}
