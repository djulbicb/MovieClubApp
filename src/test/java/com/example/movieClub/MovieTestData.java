package com.example.movieClub;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;

public class MovieTestData {

    public static Movie movieBuilder(String name, String genre) {
        return Movie.builder().name(name).genre(genre).build();
    }

    public static MovieCopy movieCopyBuilder(int copyNumber, Movie movie) {
        return MovieCopy.builder().copyNumber(copyNumber).movie(movie).build();
    }

    public static User userBuilder(String name, String email) {
        return User.builder().name(name).email(email).build();
    }
}
