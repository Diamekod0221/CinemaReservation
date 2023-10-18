package com.ramcel.cinema.reservation.db;

import com.ramcel.cinema.reservation.ticket.TicketType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "tickets")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TicketEntity extends BaseEntity{

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;

    @ManyToOne
    @JoinColumn(name = "screening_date_time")
    private LocalDateTime screeningDate;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    private TicketType type;
}
