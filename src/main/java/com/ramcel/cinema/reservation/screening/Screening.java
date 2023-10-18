package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.MovieEntity;
import com.ramcel.cinema.reservation.db.RoomEntity;

import java.time.LocalDateTime;

public record Screening(long id, MovieEntity movieEntity, RoomEntity roomEntity, LocalDateTime dateTime) {
}
