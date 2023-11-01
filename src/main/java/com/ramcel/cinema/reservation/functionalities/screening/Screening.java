package com.ramcel.cinema.reservation.functionalities.screening;

import java.time.LocalDateTime;

public record Screening(long id, Movie movie, long roomId, LocalDateTime dateTime) {
}
