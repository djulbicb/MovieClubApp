package com.example.movieClub.service;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.movieClub.model.dto.MovieDtoMapper.*;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtoList = entitiesToDtos(movies);

        return movieDtoList;
    }

    public MovieDto getMovieById(Long id){
        return entityToDto(findById(id));
    }

    private Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie with id = " + id + " not found"));
    }

    public void deleteMovieById(Long id){
        movieRepository.deleteById(id);
    }

    public MovieDto createMovie(MovieDto movieDto){
        Movie movie = dtoToEntity(movieDto);
        movieRepository.save(movie);
        return movieDto;
    }

    public MovieDto updateMovie(MovieDto movieDto, Long id){
        Movie movie = findById(id);
        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        movieRepository.save(movie);
        return entityToDto(movie);
    }

    public List<MovieDto> findByGenre(String genre){
        return entitiesToDtos(movieRepository.findByGenreOrderByNameAsc(genre));
    }
}
