package com.example.movieClub.service;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.movieClub.model.dto.MovieDtoMapper.*;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieCopyRepository movieCopyRepository;
    private final UserService userService;

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

    public MovieCopyDto rentMovieCopy(Long movieId) {
        Movie movie = findById(movieId);
        MovieCopy availableCopy = findAvailableCopy(movie);
        User user = userService.findLoggedInUser();
        if (user.getRentedCopies().size() < 3) {
            availableCopy.setRentalDate(LocalDate.now());
            availableCopy.setUser(user);
        }
        else {
            throw new RuntimeException("User already has more than 2 copies rented.");
        }
        MovieCopy savedMovieCopy = movieCopyRepository.save(availableCopy);
        return MovieCopyDtoMapper.entityToDto(savedMovieCopy);
    }

    private MovieCopy findAvailableCopy(Movie movie) {
        return movie.getMovieCopies().stream().filter(movieCopy -> isAvailable(movieCopy))
                .findFirst().orElseThrow(() -> new RuntimeException("There are no available copies for movie " + movie.getName()));
    }

    private boolean isAvailable(MovieCopy movieCopy) {
        return movieCopy.getRentalDate() == null;
    }

}
