package com.ramcel.cinema.reservation.db;

import com.ramcel.cinema.reservation.screening.Movie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Entity
@Table(name = "movies")
@Data
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
