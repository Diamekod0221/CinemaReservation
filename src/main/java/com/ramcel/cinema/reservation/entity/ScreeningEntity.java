package com.ramcel.cinema.reservation.entity;

import com.ramcel.cinema.reservation.screening.Screening;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "screenings")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScreeningEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movieEntity;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity roomEntity;

    @Column(columnDefinition = "TIMESTAMP", name = "screening_start_time")
    private LocalDateTime dateTime;

    public Screening mapToScreening(){
        return new Screening(this.getId(),
                this.movieEntity.mapToMovie(),
                this.roomEntity.getId(),
                this.dateTime
                );
    }
}
