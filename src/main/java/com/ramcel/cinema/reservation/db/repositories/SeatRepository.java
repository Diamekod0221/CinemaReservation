package com.ramcel.cinema.reservation.db.repositories;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("SELECT s FROM SeatEntity s WHERE s.screening_id = :screeningId")
    List<SeatEntity> findSeatByScreening(
            @Param("screeningId") Long screeningId);

    @Query("SELECT s FROM SeatEntity s WHERE s.room_row_id = :roomRow AND s.screening_id = :screeningId")
    List<SeatEntity> findSeatsByRoomRowAndScreeningID(
            @Param("roomRow") Long roomRowId,
            @Param("screeningId") Long screeningId);


}