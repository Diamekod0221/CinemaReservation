package com.ramcel.cinema.reservation.screening;


import com.ramcel.cinema.reservation.db.MovieEntity;

public record Movie(String name, long runTimeInSeconds) {

    public MovieEntity mapToEntity(){
        return new MovieEntity(name, runTimeInSeconds);
    }
}
