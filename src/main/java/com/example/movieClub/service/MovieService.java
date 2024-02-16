package com.example.movieClub.service;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.movieClub.model.dto.MovieDtoMapper.entitiesToDtos;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieDto getMovie(){

        Movie movie = Movie.builder().name("hari").genre("deciji").build();
        MovieDto movieDto = MovieDto.builder().name(movie.getName()).genre(movie.getGenre()).build();
        //cuvanje u bazu
        return movieDto;
    }
    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtoList = entitiesToDtos(movies);
        return movieDtoList;
    }
}
