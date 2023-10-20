package com.ramcel.cinema.reservation.functionalities.ticket.validators;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;


//todo: check if ticket is available
@Component
public class TicketValidator {

    @Autowired
    private SeatRepository seatRepository;


    public boolean isValid(Ticket ticket){
        return passedBasicValidation(ticket) &&  validateRowPosition(ticket);
    }

    public boolean isValid(List<Ticket> ticketList){
        boolean hasBasicParamsValid = ticketList.stream()
                .map(this::passedBasicValidation)
                .reduce((t, k) -> t && k)
                .orElse(false);


    }

    private boolean passedBasicValidation(Ticket ticket){
        boolean hasASeat = (ticket.getSeatId() > 0);
        boolean hasValidHolder = HolderValidator.validateHolder(ticket);
        return  hasASeat && hasValidHolder;
    }

    private boolean validateRowPosition(Ticket ticket){
        SeatEntity seatToBeBooked = seatRepository.findById(ticket.getSeatId())
                .filter(s -> s.getStatus() == SeatStatus.AVAILABLE)
                .orElseThrow(IllegalStateException::new);

        return canBookSeatWithoutHoles(seatToBeBooked);
    }

    private boolean canBookSeatWithoutHoles(SeatEntity seat){
        SeatEntity[] seats = (SeatEntity[]) seatRepository.findSeatsByRoomRowAndScreeningID(seat.getRoomRow().getId(), seat.getScreening().getId())
                .stream()
                .sorted(Comparator.comparingInt(SeatEntity::getSeatNumber))
                .toArray();

        return IntStream.range(0, seats.length - 2)
                .anyMatch(checkForHoles(seats));
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
