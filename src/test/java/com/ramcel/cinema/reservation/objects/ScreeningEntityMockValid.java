package com.ramcel.cinema.reservation.objects;

import com.ramcel.cinema.reservation.db.entity.RoomEntity;
import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;

import java.time.LocalDateTime;

public class ScreeningEntityMockValid extends ScreeningEntity {

    public ScreeningEntityMockValid(){
        super(
                new MovieEntityMock(),
                new RoomEntity(),
                LocalDateTime.now().plusDays(2)
        );
        this.setId(1L);
    }
}
