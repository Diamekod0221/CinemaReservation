package com.ramcel.cinema.reservation.seat;

import com.ramcel.cinema.reservation.screening.RoomEntity;
import com.ramcel.cinema.reservation.screening.RoomRowEntity;
import com.ramcel.cinema.reservation.screening.ScreeningEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SeatEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    static enum SeatStatus {AVAILABLE, RESERVED, BOUGHT};

    private SeatStatus status;
}
