package com.example.movieClub.exception;

public class NoAvailableCopiesException extends RuntimeException{

    public NoAvailableCopiesException(String message) {
        super(message);
    }
}
