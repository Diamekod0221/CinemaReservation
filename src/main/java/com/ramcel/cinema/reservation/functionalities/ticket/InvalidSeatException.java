package com.ramcel.cinema.reservation.functionalities.ticket;

public class InvalidSeatException extends IllegalArgumentException{

    public InvalidSeatException(String s) {
        super(s);
    }

    public InvalidSeatException(String message, Throwable cause) {
        super(message, cause);
    }

}
