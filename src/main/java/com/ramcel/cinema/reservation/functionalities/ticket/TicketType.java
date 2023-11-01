package com.ramcel.cinema.reservation.functionalities.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum TicketType {
    ADULT(BigDecimal.valueOf(25)),
    STUDENT(BigDecimal.valueOf(18)),
    CHILD(BigDecimal.valueOf(12.5));

    private final BigDecimal price;


}
