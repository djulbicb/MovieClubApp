package com.example.movieClub;

import com.example.movieClub.model.Movie;

public class MovieTestData {

    public static Movie movieBuilder(String name) {
        return Movie.builder().name(name).build();
    }
}
