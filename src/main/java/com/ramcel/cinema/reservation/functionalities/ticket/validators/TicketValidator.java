package com.ramcel.cinema.reservation.functionalities.ticket.validators;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

@Component
public class TicketValidator {

    @Autowired
    private SeatRepository seatRepository;


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
                .map(List::toArray)
                .map(v -> IntStream.range(0, v.length - 2)
                .anyMatch(checkForHoles((SeatEntity[]) v)))
                .reduce((x,y) -> x&&y).orElse(false);
    }

    private boolean passedBasicValidation(Ticket ticket){
        boolean hasASeat = (ticket.getSeatId() > 0);
        boolean hasValidHolder = HolderValidator.validateHolder(ticket);
        return  hasASeat && hasValidHolder;
    }

    private Boolean passedBasicValidation(List<Ticket> ticketList) {
        return ticketList.stream()
                .map(this::passedBasicValidation)
                .reduce((t, k) -> t && k)
                .orElse(false);
    }

    private List<SeatEntity> getBookedSeatsInRow(Long rowId, Long currentScreening) {
        return seatRepository.findSeatsByRoomRowAndScreeningID(rowId, currentScreening)
                .stream().filter(SeatEntity::isOccupied).toList();
    }

    private boolean hasMultipleScreenings(List<Ticket> ticketList){
        return ticketList.stream().mapToLong(Ticket::getScreeningId).distinct().sum() > 1;
    }


    private SeatEntity fetchSeatEntity(Ticket ticket) {
        return seatRepository.findById(ticket.getSeatId())
                .filter(s -> !s.isOccupied())
                .orElseThrow(IllegalStateException::new);
    }


    private static IntPredicate checkForHoles(SeatEntity[] seats) {
        return i -> {
            SeatEntity seat1 = seats[i];
            SeatEntity seat2 = seats[i + 1];
            SeatEntity seat3 = seats[i + 2];

            return seat1.getStatus() != SeatStatus.AVAILABLE
                    && seat2.getStatus() == SeatStatus.AVAILABLE
                    && seat3.getStatus() != SeatStatus.AVAILABLE;
        };
    }

}
