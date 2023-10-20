package com.ramcel.cinema.reservation.functionalities.ticket.validators;

import com.ramcel.cinema.reservation.functionalities.ticket.Ticket;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class HolderValidator {
    private HolderValidator() {
    }

    public static boolean validateHolder(Ticket ticket) {
        return validateName(ticket.getName()) && validateSurname(ticket.getSurname());
    }

    private static boolean  validateName(String name) {
        return isChars(name) && checksNameConditions(name);
    }

    private static boolean isChars(String name) {
        return Arrays.stream(name.split(""))
                .filter(s -> !s.isEmpty())
                .map(s -> Character.isLetter(s.charAt(0)))
                .reduce((s, v) -> s && v).orElse(false);
    }

    private static boolean  checksNameConditions(String name) {
        return isValidUppercaseStart(name) && name.length() >= 3;
    }

    private static boolean  isValidUppercaseStart(String name) {
        String[] stringArray = name.split("");
        String first = stringArray[0];
        return (!first.isEmpty() && first.equals(first.toUpperCase()));
    }

    private static boolean  validateSurname(String surname) {
        return Optional.of(surname)
                .filter(canBeDividedAtDash(surname))
                .map(s -> List.of(
                        surname.substring(0, surname.indexOf('-')),
                        surname.substring(surname.indexOf('-') + 1)))
                .orElse(List.of(surname))
                .stream()
                .map(HolderValidator::validateName)
                .reduce((s, v) -> s && v)
                .get();
    }

    private static Predicate<String> canBeDividedAtDash(String surname) {
        return s -> (surname.indexOf('-') > 0) && (surname.indexOf('-') < surname.length() - 1);
    }
}