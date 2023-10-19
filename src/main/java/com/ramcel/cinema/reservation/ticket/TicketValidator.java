package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketValidator {

    @Autowired
    private SeatRepository seatRepository;


    public void validateTicket(Ticket ticket){
        boolean hasValidHolder = validateHolder(ticket);


    }

    private boolean validateHolder(Ticket ticket){
        return validateName(ticket.getName()) && validateSurname(ticket.getSurname());
    }

    private boolean validateName(String name){
        String[] stringArray = name.split("");
        String first = stringArray[0];
        return isValidUppercase(first) && name.length() >=3;
    }

    private boolean isValidUppercase(String letter){
        return (!letter.isEmpty() && letter.equals(letter.toUpperCase()));
    }

    private boolean validateSurname(String surname) {
        return Optional.of(surname)
                .filter(s -> surname.indexOf('-') > 0)
                .map(s -> List.of(
                        surname.substring(0, surname.indexOf('-')),
                        surname.substring(surname.indexOf('-') + 1)))
                .orElse(List.of(surname))
                .stream()
                .map(this::validateName)
                .reduce((s, v) -> s && v)
                .get();
    }

    public void validateRowPosition(Ticket ticket){

    }
}
