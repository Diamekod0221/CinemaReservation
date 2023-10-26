package com.ramcel.cinema.reservation.functionalities.ticket.validators;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.seat.Seat;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TicketValidator {

    @Autowired
    private SeatRepository seatRepository;

    public TicketValidator(SeatRepository repository){
        this.seatRepository = repository;
    }

    public boolean isValid(Ticket ticket){
        return  this.isValid(List.of(ticket));
    }


    public boolean isValid(List<Ticket> ticketList) throws IllegalReservationException{
        if (hasMultipleScreenings(ticketList)) {
            throw new IllegalReservationException("Multiple screenings chosen");
        }
        else {
            boolean hasBasicParamsValid = passedBasicValidation(ticketList);

            boolean hasValidRows = validateRowPosition(ticketList);

            return hasValidRows && hasBasicParamsValid;
        }
    }

    private boolean validateRowPosition(List<Ticket> ticketList) {

        Map<Long, List<SeatEntity>> bookedSeatsByRowMap = new HashMap<>();
        for(Ticket ticket: ticketList){
            SeatEntity currentSeat = fetchSeatEntity(ticket);

            Long rowId = currentSeat.getRoomRow().getId();
            Long currentScreening = ticket.getScreeningId();
            List<SeatEntity> bookedSeatsInRow = getBookedSeatsInRow(rowId, currentScreening);

            bookedSeatsByRowMap.computeIfAbsent(rowId, v ->  bookedSeatsInRow).add(currentSeat);
        }

        return canBookSeatWithoutHoles(bookedSeatsByRowMap);
    }

    private static boolean canBookSeatWithoutHoles(Map<Long, List<SeatEntity>> bookedSeatsByRowMap) {
        return bookedSeatsByRowMap.values()
                .stream()
                .map(seats -> seats.stream()
                        .sorted(Comparator.comparingInt(SeatEntity::getSeatNumber))
                        .collect(Collectors.toList()))
                .anyMatch(TicketValidator::hasNoHoles);
    }

    private static boolean hasNoHoles(List<SeatEntity> seats) {
        for (int i = 0; i < seats.size() - 2; i++) {
            SeatEntity seat1 = seats.get(i);
            SeatEntity seat2 = seats.get(i + 1);
            SeatEntity seat3 = seats.get(i + 2);

            if (seat1.getStatus() != SeatStatus.AVAILABLE
                    && seat2.getStatus() == SeatStatus.AVAILABLE
                    && seat3.getStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        return true;
    }


    private boolean passedBasicValidation(Ticket ticket){
        boolean hasASeat = (ticket.getSeatId() > 0);
        boolean hasValidHolder = HolderValidator.validateHolder(ticket);
        boolean isMoreThan15MinsAway = ticket.getScreeningDate().isAfter(LocalDateTime.now().plusMinutes(15));
        return  hasASeat && hasValidHolder && isMoreThan15MinsAway;
    }

    private Boolean passedBasicValidation(List<Ticket> ticketList) {
        return ticketList.stream()
                .map(this::passedBasicValidation)
                .reduce((t, k) -> t && k)
                .orElse(false);
    }

    private List<SeatEntity> getBookedSeatsInRow(Long rowId, Long currentScreening) {
        return seatRepository.findSeatsByRoomRowAndScreeningID(rowId, currentScreening)
                .stream().filter(SeatEntity::isOccupied).collect(Collectors.toList());
    }

    private boolean hasMultipleScreenings(List<Ticket> ticketList){
        return ticketList.stream().mapToLong(Ticket::getScreeningId).distinct().sum() > 1;
    }


    private SeatEntity fetchSeatEntity(Ticket ticket) {
        return seatRepository.findById(ticket.getSeatId())
                .filter(s -> !s.isOccupied())
                .orElseThrow(IllegalStateException::new);
    }




}
