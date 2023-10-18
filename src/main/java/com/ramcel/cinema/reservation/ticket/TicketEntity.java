package com.ramcel.cinema.reservation.ticket;

import com.ramcel.cinema.reservation.screening.ScreeningEntity;
import com.ramcel.cinema.reservation.seat.SeatEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class TicketEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
