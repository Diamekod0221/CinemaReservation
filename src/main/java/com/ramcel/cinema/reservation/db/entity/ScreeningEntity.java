package com.ramcel.cinema.reservation.db.entity;

import com.ramcel.cinema.reservation.functionalities.screening.Screening;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "screenings")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
