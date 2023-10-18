package com.ramcel.cinema.reservation.screening;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numberOfRows;

    @OneToMany(mappedBy = "cinemaRoom")
    private List<RoomRowEntity> roomRows;

    public RoomEntity(List<RoomRowEntity> roomRows){
        this.numberOfRows = roomRows.size();
        this.roomRows = roomRows;
    }

}
