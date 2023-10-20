package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Ticket {
    private final String name;

    private final String surname;

    private final long screeningId;

    private final LocalDateTime screeningDate;

    private final long seatId;

    private final TicketType type;

    @Override
    public String toString(){
        return "Holder surname: " + surname + " , screening: " + screeningId + " ,seat: " + seatId;
    }


}
