package com.ramcel.cinema.reservation.screening;

import com.ramcel.cinema.reservation.db.ScreeningEntity;
import com.ramcel.cinema.reservation.db.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService{

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    public List<Screening> findScreenings(LocalDateTime dateTime) {
        LocalDateTime searchStartDate = dateTime.minusMinutes(30);
        LocalDateTime searchEndDate = dateTime.plusHours(4);

        return screeningsListInPeriod(searchStartDate, searchEndDate);
    }

    private List<Screening> screeningsListInPeriod(LocalDateTime searchStartDate, LocalDateTime searchEndDate) {

        return screeningRepository.findScreeningsInPeriod(searchStartDate, searchEndDate)
                .stream().map(ScreeningEntity::mapToScreening).toList();
    }

    @Override
    public List<Screening> findScreenings(Movie movie, LocalDateTime dateTime) {
        return findScreenings(dateTime).stream()
                .filter(s -> s.movie().equals(movie))
                .toList();
    }
}
