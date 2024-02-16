package com.example.movieClub.controller;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
//    // da li smo ovde mogli koristiti AllArgsConstructor?
//    public MovieController(MovieService movieService) {
//        this.movieService = movieService;
//    }

    @RequestMapping(method = RequestMethod.GET)
    public MovieDto getMovie() {
        return movieService.getMovie();
    }
    @GetMapping("/allMovies")
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }
}
