package com.ramcel.cinema.reservation.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Entity
@Table(name = "screenings")
@EqualsAndHashCode(callSuper = true)
@Data
public class ScreeningEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movieEntity;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity roomEntity;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;
}
