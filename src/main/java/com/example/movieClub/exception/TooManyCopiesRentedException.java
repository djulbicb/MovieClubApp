package com.example.movieClub.exception;

public class TooManyCopiesRentedException extends RuntimeException {

    public TooManyCopiesRentedException(String message) {
        super(message);
    }
}
