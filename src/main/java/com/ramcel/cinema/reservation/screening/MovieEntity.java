package com.ramcel.cinema.reservation.screening;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class MovieEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movieId;

    private String name;

    private Duration runTime;

    public MovieEntity(String name, Duration runTime){
        this.name = name;
        this.runTime=runTime;
    }

    public MovieEntity(String name, String runTime){
        this(name, Duration.parse(runTime));
    }
}
