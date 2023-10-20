package com.ramcel.cinema.reservation.db.repositories;

import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

}
