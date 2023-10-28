package com.ramcel.cinema.reservation.functionalities.ticket.validators;

import com.ramcel.cinema.reservation.db.entity.SeatEntity;
import com.ramcel.cinema.reservation.db.repositories.SeatRepository;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalReservationException;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalSeatException;
import com.ramcel.cinema.reservation.functionalities.exception.IllegalTicketException;
import com.ramcel.cinema.reservation.functionalities.seat.SeatStatus;
import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
            return performValidation(ticketList);
        }
    }

    private boolean performValidation(List<Ticket> ticketList) {
        try {
            boolean hasBasicParamsValid = passedBasicValidation(ticketList);

            boolean hasValidRows = validateRowPosition(ticketList);

            return hasValidRows && hasBasicParamsValid;
        }
        catch (IllegalTicketException e){
            throw new IllegalReservationException();
        }
    }

    private boolean validateRowPosition(List<Ticket> ticketList) throws IllegalTicketException {

        try{
            return performRowValidation(ticketList);
        }
        catch (IllegalSeatException e){
            throw new IllegalTicketException();
        }
    }

    private boolean performRowValidation(List<Ticket> ticketList) {
        Map<Long, List<SeatEntity>> seatsByRowMap = computeSeatsByRowMap(ticketList);
        reserveSeatsFromList(ticketList, seatsByRowMap);

        return canBookSeatWithoutHoles(seatsByRowMap);
    }

    private static void reserveSeatsFromList(List<Ticket> ticketList, Map<Long, List<SeatEntity>> seatsByRowMap) {
        Set<Long> seatsToBeBookedIds = ticketList.stream().map(Ticket::getSeatId).collect(Collectors.toSet());

        for(List<SeatEntity> seatsInRow: seatsByRowMap.values()){
            seatsInRow.stream().filter(v -> seatsToBeBookedIds.contains(v.getId())).forEach(SeatEntity::reserve);
        }
    }

    private Map<Long, List<SeatEntity>> computeSeatsByRowMap(List<Ticket> ticketList) {
        Map<Long, List<SeatEntity>> bookedSeatsByRowMap = new HashMap<>();
        for(Ticket ticket: ticketList){
            SeatEntity currentSeat = fetchSeatEntity(ticket);
            if(currentSeat.isOccupied()){
                throw new IllegalSeatException();
            }

            addRowToMap(ticket, currentSeat, bookedSeatsByRowMap);
        }
        return bookedSeatsByRowMap;
    }

    private void addRowToMap(Ticket ticket, SeatEntity currentSeat, Map<Long, List<SeatEntity>> bookedSeatsByRowMap) {
        Long rowId = currentSeat.getRoomRow().getId();
        Long currentScreening = ticket.getScreeningId();
        List<SeatEntity> seatsInRow = getSeatsInRow(rowId, currentScreening);

        bookedSeatsByRowMap.computeIfAbsent(rowId, v ->  seatsInRow);
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
        if(seats.size() == 3){
            if(hasAHole(seats)){
                return false;
            }
        }
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

    private static boolean hasAHole(List<SeatEntity> seats) {
        return seats.get(0).getStatus() != SeatStatus.AVAILABLE && seats.get(1).getStatus() == SeatStatus.AVAILABLE && seats.get(2).getStatus() != SeatStatus.AVAILABLE;
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

    private List<SeatEntity> getSeatsInRow(Long rowId, Long currentScreening) {
        return new ArrayList<>(seatRepository.findSeatsByRoomRowIdAndScreeningID(rowId, currentScreening));
    }

    private boolean hasMultipleScreenings(List<Ticket> ticketList){
        return ticketList.stream().mapToLong(Ticket::getScreeningId).distinct().count() > 1;
    }


    private SeatEntity fetchSeatEntity(Ticket ticket) throws IllegalSeatException {
        return seatRepository.findById(ticket.getSeatId())
                .orElseThrow(IllegalSeatException::new);
    }




}
