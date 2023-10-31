package com.ramcel.cinema.reservation.db.repositories;

import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByExpirationDateBefore(LocalDateTime now);
}
