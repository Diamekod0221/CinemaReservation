package com.ramcel.cinema.reservation.functionalities.screening;


import com.ramcel.cinema.reservation.db.entity.MovieEntity;

public record Movie(String name, long runTimeInSeconds) {

    public MovieEntity mapToEntity(){
        return new MovieEntity(name, runTimeInSeconds);
    }
}
