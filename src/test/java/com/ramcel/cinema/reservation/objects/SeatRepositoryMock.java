package com.ramcel.cinema.reservation.objects;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface SeatRepositoryMock extends SeatRepository {

    @Override
    default List<SeatEntity> findSeatByScreening(Long screeningId){
        return List.of(SeatEntityMockValid.withScreening(screeningId));
    }

    @Override
    default List<SeatEntity> findSeatsByRoomRowAndScreeningID(Long roomRowId, Long screeningId){
        return List.of(SeatEntityMockValid.withScreeningAndRow(roomRowId, screeningId));
    }

    @Override
    @Nonnull
    default Optional<SeatEntity> findById(Long Id){
        return Optional.of(new SeatEntityMockValid(Id));
    }
}
