package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.MovieEntity;
import com.ramcel.cinema.reservation.db.RoomEntity;

import java.time.LocalDateTime;

public record Screening(long id, Movie movie, long roomId, LocalDateTime dateTime) {
}
