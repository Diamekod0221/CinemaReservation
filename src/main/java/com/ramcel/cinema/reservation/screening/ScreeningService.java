package com.ramcel.cinema.reservation.screening;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {

    public List<Screening> findScreenings(LocalDateTime dateTime);

    public List<Screening> findScreenings(Movie movie, LocalDateTime dateTime);
}
