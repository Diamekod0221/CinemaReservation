package com.ramcel.cinema.reservation.db.entity;

import com.ramcel.cinema.reservation.functionalities.screening.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

@Entity
@Table(name = "movies")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MovieEntity extends BaseEntity {

    private String name;

    private long runTimeInSeconds;

    public MovieEntity(String name, long runTimeInSeconds){
        this.name = name;
        this.runTimeInSeconds=runTimeInSeconds;
    }

    public MovieEntity(String name, String runTime){
        this(name, Integer.parseInt(runTime));
    }

    public Duration getRunTimeDuration(){
        return Duration.ofSeconds(this.runTimeInSeconds);
    }

    public Movie mapToMovie(){
        return new Movie(this.name, this.runTimeInSeconds);
    }
}
