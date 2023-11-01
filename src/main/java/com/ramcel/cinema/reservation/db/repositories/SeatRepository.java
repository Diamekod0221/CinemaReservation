package com.ramcel.cinema.reservation.db.repositories;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("SELECT s FROM SeatEntity s WHERE s.screening.id = :screeningId")
    List<SeatEntity> findSeatByScreeningId(
            @Param("screeningId") Long screeningId);

    @Query("SELECT s FROM SeatEntity s WHERE s.roomRow.id = :roomRow AND s.screening.id = :screeningId")
    List<SeatEntity> findSeatsByRoomRowIdAndScreeningID(
            @Param("roomRow") Long roomRowId,
            @Param("screeningId") Long screeningId);


}
