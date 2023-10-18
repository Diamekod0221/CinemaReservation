package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.RoomEntity;
import jakarta.persistence.ManyToOne;

public record RoomRow(long rowId, int seatsInRow, RoomEntity cinemaRoom) {
}
