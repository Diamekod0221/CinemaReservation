package com.ramcel.cinema.reservation.functionalities.ticket;

import com.ramcel.cinema.reservation.db.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ticket {
    private String name;

    private String surname;

    private long screeningId;

    private LocalDateTime screeningDate;

    private long seatId;

    private TicketType type;

    @Override
    public String toString(){
        return "Holder surname: " + surname + " , screening: " + screeningId + " ,seat: " + seatId;
    }

}
