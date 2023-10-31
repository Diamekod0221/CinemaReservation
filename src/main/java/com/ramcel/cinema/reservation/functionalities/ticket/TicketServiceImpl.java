package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import com.ramcel.cinema.reservation.db.repositories.TicketRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalSeatException;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalTicketException;
import com.ramcel.cinema.reservation.functionalities.reservation.Reservation;
import com.ramcel.cinema.reservation.functionalities.ticket.validators.TicketValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper mapper;

    @Autowired
    private TicketValidator validator;


    @Override
    public Reservation bookTicket(Ticket ticket) throws IllegalReservationException {
        if (validator.isValid(ticket)) {
            return handleReservation(ticket);
        } else {
            throw new IllegalReservationException("Ticket: " + ticket + " is not valid.");
        }
    }

    private Reservation handleReservation(Ticket ticket) throws IllegalReservationException{
        manageEntitiesState(ticket);

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);
        BigDecimal price = ticket.getType().getPrice();

        return new Reservation(List.of(ticket), price, expirationDate);
    }

    @Override
    public Reservation bookTicket(List<Ticket> ticketList) throws IllegalReservationException {
            if (validator.isValid(ticketList)) {
                return handleReservation(ticketList);
            } else {
                throw new IllegalTicketException("Ticket list" + ticketList + " is not valid.");
            }
    }

    private Reservation handleReservation(List<Ticket> ticketList) throws IllegalReservationException {
        ticketList.forEach(this::manageEntitiesState);

        BigDecimal totalPrice = ticketList.stream()
                .map(t -> t.getType().getPrice())
                .reduce(BigDecimal::add).orElseThrow(IllegalArgumentException::new);

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);

        return new Reservation(ticketList, totalPrice, expirationDate);
    }

    private void manageEntitiesState(Ticket ticket) throws IllegalTicketException{
        TicketEntity ticketEntity = mapper.mapToEntity(ticket);
        ticketRepository.save(ticketEntity);

        SeatEntity seatForTicket = ticketEntity.getSeat();
        reserveSeat(seatForTicket);
    }

    @Transactional
    public void reserveSeat(SeatEntity seat) throws IllegalSeatException {
        if (seat != null) {
            seat.reserve();
        }
    }

    @Scheduled(cron = "0 0 2 * * *") // Runs every day at 2:00 AM
    public void deleteExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<TicketEntity> expiredReservations = ticketRepository.findByExpirationDateBefore(now);
        ticketRepository.deleteAll(expiredReservations);
    }




}
