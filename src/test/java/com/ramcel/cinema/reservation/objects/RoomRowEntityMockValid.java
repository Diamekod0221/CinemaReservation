package com.ramcel.cinema.reservation.objects;

import com.ramcel.cinema.reservation.db.entity.RoomEntity;
import com.ramcel.cinema.reservation.db.entity.RoomRowEntity;

public class RoomRowEntityMockValid extends RoomRowEntity {

    public RoomRowEntityMockValid(){
        super(1L,10, new RoomEntity());
    }

}
