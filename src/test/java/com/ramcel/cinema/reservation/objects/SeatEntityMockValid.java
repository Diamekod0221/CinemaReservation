package com.ramcel.cinema.reservation.objects;

import com.ramcel.cinema.reservation.db.entity.RoomEntity;
import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;

public class SeatEntityMockValid extends SeatEntity {
    public SeatEntityMockValid(){
        super(new RoomEntity(),
                new RoomRowEntityMockValid(),
                1,
                new ScreeningEntityMockValid(),
                false,
                SeatStatus.AVAILABLE
                );
        this.setId(1L);
    }

    public SeatEntityMockValid(Long id){
        this();
        this.setId(id);
    }

    public static SeatEntityMockValid withScreeningAndRow(Long roomRowId, Long screeningId){
        SeatEntityMockValid mock = SeatEntityMockValid.withScreening(screeningId);
        mock.getRoomRow().setId(roomRowId);
        return mock;
    }
    public static SeatEntityMockValid withScreening(Long screeningId){
        SeatEntityMockValid mock = new SeatEntityMockValid();
        mock.getScreening().setId(screeningId);

        return mock;
    }
}
