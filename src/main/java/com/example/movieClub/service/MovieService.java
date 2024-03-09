package com.example.movieClub.service;

import com.example.movieClub.exception.EntityNotFoundException;
import com.example.movieClub.exception.NoAvailableCopiesException;
import com.example.movieClub.exception.TooManyCopiesRentedException;
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

import static com.example.movieClub.model.dto.MovieDtoMapper.*;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieCopyRepository movieCopyRepository;
    private final UserService userService;

    private static final int ALLOWED_NUMBER_OF_COPIES = 3;

    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtoList = entitiesToDtos(movies);

        return movieDtoList;
    }

    public MovieDto getMovieById(Long id){
        return entityToDto(findMovieById(id));
    }

    private Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie with id = " + id + " not found"));
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
        Movie movie = findMovieById(id);
        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        return entityToDto(movieRepository.save(movie));
    }

    public List<MovieDto> findByGenre(String genre){
        return entitiesToDtos(movieRepository.findByGenreOrderByNameAsc(genre));
    }

    public MovieCopyDto rentMovieCopy(Long movieId) {
        Movie movie = findMovieById(movieId);
        MovieCopy availableCopy = findAvailableCopy(movie);
        User user = userService.findLoggedInUser();
        if (user.getRentedCopies().size() < ALLOWED_NUMBER_OF_COPIES) {
            availableCopy.setRentalDate(LocalDate.now());
            availableCopy.setUser(user);
        }
        else {
            throw new TooManyCopiesRentedException("User already has " + ALLOWED_NUMBER_OF_COPIES + " copies rented.");
        }
        MovieCopy savedMovieCopy = movieCopyRepository.save(availableCopy);
        return MovieCopyDtoMapper.entityToDto(savedMovieCopy);
    }

    private MovieCopy findAvailableCopy(Movie movie) {
        return movie.getMovieCopies().stream().filter(movieCopy -> isAvailable(movieCopy))
                .findFirst().orElseThrow(() -> new NoAvailableCopiesException("There are no available copies for movie " + movie.getName()));
    }

    private boolean isAvailable(MovieCopy movieCopy) {
        return movieCopy.getRentalDate() == null;
    }

}
