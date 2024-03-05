package com.example.movieClub.service;

import com.example.movieClub.MovieTestData;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.model.dto.MovieDtoMapper;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.movieClub.MovieTestData.movieBuilder;
import static com.example.movieClub.model.dto.MovieDtoMapper.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieCopyRepository movieCopyRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MovieService movieService;

    @Test
    public void shouldGetAllMovies() {
        List<Movie> movies = List.of(movieBuilder("Harry Potter", "Fantasy"), movieBuilder("LOTR", "Fantasy"));
        when(movieRepository.findAll()).thenReturn(movies);

        assertThat(movieService.getMovies()).isEqualTo(entitiesToDtos(movies));
    }

    @Test
    public void shouldGetMovieById() {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieRepository.findById(1l)).thenReturn(Optional.of(movie));

        assertThat(movieService.getMovieById(1l)).isEqualTo(entityToDto(movie));
    }

    @Test
    public void shouldDeleteMovieById() {
        movieService.deleteMovieById(1l);
        verify(movieRepository, Mockito.times(1)).deleteById(1l);
    }

    @Test
    public void shouldCreateMovie() {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieRepository.save(movie)).thenReturn(movie);
        assertThat(movieService.createMovie(entityToDto(movie))).isEqualTo(entityToDto(movie));
    }

    @Test
    public void shouldUpdateMovie() {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieRepository.findById(1l)).thenReturn(Optional.ofNullable(movie));
        MovieDto movieDto = MovieDto.builder().name("LOTR").build();
        when(movieRepository.save(movie)).thenReturn(movie);
        assertThat(movieService.updateMovie(movieDto, 1l)).isEqualTo(movieDto);
    }

    @Test
    public void shouldRentMovieCopy() {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieRepository.findById(1l)).thenReturn(Optional.ofNullable(movie));
        MovieCopy movieCopy = MovieCopy.builder().copyNumber(1).movie(movie).build();
        movie.setMovieCopies(List.of(movieCopy));
        User user = User.builder().name("Isidora").build();
        when(userService.findLoggedInUser()).thenReturn(user);
        user.setRentedCopies(List.of());
        when(movieCopyRepository.save(movieCopy)).thenReturn(movieCopy);
        assertThat(movieService.rentMovieCopy(1l)).isEqualTo(MovieCopyDtoMapper.entityToDto(movieCopy));
    }

    @Test
    public void shouldFindByGenre() {
//        String genre = "Fantasy";
        Movie movie1 = movieBuilder("Dune", "Fantasy");
        Movie movie2 = movieBuilder("Harry Potter", "Fantasy");
        List<Movie> movies = List.of(movie1, movie2);
        when(movieRepository.findByGenreOrderByNameAsc("Fantasy")).thenReturn(movies);
        assertThat(movieService.findByGenre("Fantasy")).isEqualTo(entitiesToDtos(movies));
    }
}