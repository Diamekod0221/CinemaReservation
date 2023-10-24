package com.ramcel.cinema.reservation.functionalities.exception;

public class MultipleScreeningException extends IllegalArgumentException{

    public MultipleScreeningException(String s) {
        super(s);
    }

    public MultipleScreeningException(String message, Throwable cause) {
        super(message, cause);
    }
}
