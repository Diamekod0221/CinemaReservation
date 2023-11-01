package com.ramcel.cinema.reservation.functionalities.screening;

import com.ramcel.cinema.reservation.db.entity.ScreeningEntity;
import com.ramcel.cinema.reservation.db.repositories.ScreeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ScreeningServiceImpl implements ScreeningService{

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    public List<Screening> findScreenings(LocalDateTime dateTime) {
        LocalDateTime searchStartDate = dateTime.minusMinutes(30);
        LocalDateTime searchEndDate = dateTime.plusHours(4);

        List<Screening> foundScreenings = screeningsListInPeriod(searchStartDate, searchEndDate);

        return sortScreeningsByMovieNameAndRunTime(foundScreenings);
    }

    private List<Screening> screeningsListInPeriod(LocalDateTime searchStartDate, LocalDateTime searchEndDate) {
        return screeningRepository.findScreeningsInPeriod(searchStartDate, searchEndDate)
                .stream().peek(v -> log.atDebug().log("Fetched: " + v.getId()))
                .map(ScreeningEntity::mapToScreening).toList();
    }

    @Override
    public List<Screening> findScreenings(Movie movie, LocalDateTime dateTime) {
        return findScreenings(dateTime).stream()
                .filter(s -> s.movie().equals(movie))
                .toList();
    }

    public static List<Screening> sortScreeningsByMovieNameAndRunTime(List<Screening> screenings) {
        Comparator<Screening> comparator = Comparator
                .comparing((Screening screening) -> screening.movie().name())
                .thenComparingLong(screening -> screening.movie().runTimeInSeconds());

        return List.copyOf(screenings).stream().sorted(comparator).toList();
    }
}
