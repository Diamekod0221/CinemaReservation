package com.ramcel.cinema.reservation.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "room_rows")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RoomRowEntity extends BaseEntity{

    private int seatsInRow;

    @ManyToOne
    private RoomEntity cinemaRoom;

    public RoomRowEntity(int seatsInRow, RoomEntity cinemaRoom){
        this.cinemaRoom = cinemaRoom;
        this.seatsInRow = seatsInRow;
    }

    public RoomRowEntity(long id, int seatsInRow, RoomEntity cinemaRoom){
        super(id);
        this.cinemaRoom = cinemaRoom;
        this.seatsInRow = seatsInRow;
    }


}
