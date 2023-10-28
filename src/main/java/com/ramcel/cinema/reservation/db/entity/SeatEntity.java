package com.ramcel.cinema.reservation.db.entity;

import com.ramcel.cinema.reservation.functionalities.exception.IllegalSeatException;
import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name = "seats")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    private boolean isOccupied = false;

    @Enumerated(EnumType.STRING)
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


    public void reserve() throws IllegalSeatException {
        if(status == SeatStatus.BOUGHT){
            throw new IllegalSeatException("Seat already bought!");
        }
        status = SeatStatus.RESERVED;
        isOccupied = true;
    }
    public void buy(){
        status = SeatStatus.BOUGHT;
        isOccupied = true;
    }

    public void free(){
        status = SeatStatus.AVAILABLE;
        isOccupied = false;
    }
}
