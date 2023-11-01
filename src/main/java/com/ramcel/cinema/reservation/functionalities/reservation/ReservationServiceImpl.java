package com.ramcel.cinema.reservation.functionalities.reservation;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.db.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Scheduled(cron = "0 0 2 * * *") // Runs every day at 2:00 AM
    public void deleteExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<TicketEntity> expiredReservations = ticketRepository.findByExpirationDateBefore(now);
        freeReservedSeats(expiredReservations);

        ticketRepository.deleteAll(expiredReservations);
    }

    private void freeReservedSeats(List<TicketEntity> expiredReservations) {
        List<SeatEntity> reservedSeats = expiredReservations.stream().map(TicketEntity::getSeat).toList();
        reservedSeats.forEach(SeatEntity::free);
        seatRepository.saveAll(reservedSeats);
    }
}
