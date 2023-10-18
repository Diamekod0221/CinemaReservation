package com.ramcel.cinema.reservation.screening;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RoomRowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int seatsInRow;

    @ManyToOne
    private RoomEntity cinemaRoom;

    public RoomRowEntity(int seatsInRow, RoomEntity cinemaRoom){
        this.cinemaRoom = cinemaRoom;
        this.seatsInRow = seatsInRow;
    }


}
