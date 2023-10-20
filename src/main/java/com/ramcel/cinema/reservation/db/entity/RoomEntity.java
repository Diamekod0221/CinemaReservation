package com.ramcel.cinema.reservation.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "rooms")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RoomEntity extends BaseEntity{

    private int numberOfRows;

    @OneToMany(mappedBy = "cinemaRoom", fetch = FetchType.EAGER)
    private List<RoomRowEntity> roomRows;

    public RoomEntity(List<RoomRowEntity> roomRows){
        this.numberOfRows = roomRows.size();
        this.roomRows = roomRows;
    }

}
