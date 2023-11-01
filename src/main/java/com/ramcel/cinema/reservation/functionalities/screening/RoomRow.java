package com.ramcel.cinema.reservation.functionalities.screening;

import com.ramcel.cinema.reservation.db.entity.RoomEntity;

public record RoomRow(long rowId, int seatsInRow, RoomEntity cinemaRoom) {
}
