package com.ramcel.cinema.reservation.db.entity;

import com.ramcel.cinema.reservation.functionalities.ticket.TicketType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tickets")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends BaseEntity{

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;


    @Column(name = "screening_start_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime screeningDate;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    private TicketType type;
}
