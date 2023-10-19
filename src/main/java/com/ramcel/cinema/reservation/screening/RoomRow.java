package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.entity.RoomEntity;

public record RoomRow(long rowId, int seatsInRow, RoomEntity cinemaRoom) {
}
