package com.ramcel.cinema.reservation.repositories;

import com.ramcel.cinema.reservation.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Long> {

    @Query("SELECT s FROM ScreeningEntity s WHERE s.dateTime >= :periodStart AND s.dateTime <= :periodEnd")
    List<ScreeningEntity> findScreeningsInPeriod(
            @Param("periodStart") LocalDateTime periodStart,
            @Param("periodEnd") LocalDateTime periodEnd
    );
}
