package com.example.movieClub.controller;

import com.example.movieClub.MovieTestData;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.model.dto.MovieDtoMapper;
import com.example.movieClub.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;

import static com.example.movieClub.MovieTestData.movieBuilder;
import static com.example.movieClub.MovieTestData.movieCopyBuilder;
import static com.example.movieClub.model.dto.MovieDtoMapper.entitiesToDtos;
import static com.example.movieClub.model.dto.MovieDtoMapper.entityToDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void shouldGetAll() throws Exception {
        Movie movie1 = movieBuilder("Harry Potter", "Fantasy");
        Movie movie2 = movieBuilder("Dune", "Fantasy");
        List<Movie> movies = List.of(movie1, movie2);
        when(movieService.getMovies()).thenReturn(entitiesToDtos(movies));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/movies/allMovies"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetById() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieService.getMovieById(1L)).thenReturn(entityToDto(movie));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/movies/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(
                delete("/movies/1")).andExpect(status().isOk());
        verify(movieService, Mockito.times(1)).deleteMovieById(1l);
    }

    @Test
    void shouldCreate() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        MovieDto movieDto = entityToDto(movie);
        when(movieService.createMovie(movieDto)).thenReturn(movieDto);
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(movieDto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdate() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        MovieDto movieDto = entityToDto(movie);
        when(movieService.updateMovie(movieDto, 1L)).thenReturn(movieDto);
        mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(movieDto)))
                .andExpect(status().isOk());
        verify(movieService, Mockito.times(1)).updateMovie(movieDto, 1L);
    }

    @Test
    void shouldGetByGenre() throws Exception {
        Movie movie1 = movieBuilder("Harry Potter", "Fantasy");
        Movie movie2 = movieBuilder("Dune", "Fantasy");
        List<Movie> movies = List.of(movie1, movie2);
        when(movieService.findByGenre("Fantasy")).thenReturn(entitiesToDtos(movies));
        mockMvc.perform(
                get("/movies/moviesByGenre?genre=Fantasy"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRentMovieCopy() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        MovieCopy movieCopy = movieCopyBuilder(3, movie);
        when(movieService.rentMovieCopy(1L)).thenReturn(MovieCopyDtoMapper.entityToDto(movieCopy));
        mockMvc.perform(put("/movies/rent/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(MovieCopyDtoMapper.entityToDto(movieCopy))))
                .andExpect(status().isOk())
                        .andExpect((jsonPath("$.copyNumber").value(3)));
        verify(movieService, Mockito.times(1)).rentMovieCopy(1L);
    }

    private String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
