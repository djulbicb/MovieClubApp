package com.example.movieClub.controller;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.movieClub.MovieTestData.movieBuilder;
import static com.example.movieClub.MovieTestData.movieCopyBuilder;
import static com.example.movieClub.model.dto.MovieDtoMapper.entitiesToDtos;
import static com.example.movieClub.model.dto.MovieDtoMapper.entityToDto;
import static org.mockito.Mockito.*;
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
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldGetAll() throws Exception {
        Movie movie1 = movieBuilder("Harry Potter", "Fantasy");
        Movie movie2 = movieBuilder("Dune", "Fantasy");
        List<Movie> movies = List.of(movie1, movie2);
        when(movieService.getMovies(PageRequest.of(1, 2))).thenReturn(entitiesToDtos(movies));
        when(movieService.countMovies()).thenReturn((long) movies.size());

        mockMvc.perform(get("/movies/allMovies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies").isArray())
                .andExpect(jsonPath("$.movies.length()").value(2))
                .andExpect(jsonPath("$.movies[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$.movies[0].genre").value("Fantasy"))
                .andExpect(jsonPath("$.movies[1].name").value("Dune"))
                .andExpect(jsonPath("$.movies[1].genre").value("Fantasy"))
                .andExpect(jsonPath("$.totalCount").value(2));
    }

    @Test
    void shouldGetById() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        when(movieService.getMovieById(1L)).thenReturn(entityToDto(movie));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(entityToDto(movie))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("Fantasy"));
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("Fantasy"));
    }

    @Test
    void shouldUpdate() throws Exception {
        Movie movie = movieBuilder("Harry Potter", "Fantasy");
        MovieDto movieDto = entityToDto(movie);
        when(movieService.updateMovie(movieDto, 1L)).thenReturn(movieDto);
        mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(movieDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre").value("Fantasy"));
        verify(movieService, Mockito.times(1)).updateMovie(movieDto, 1L);
    }

    @Test
    void shouldGetByGenre() throws Exception {
        Movie movie1 = movieBuilder("Harry Potter", "Fantasy");
        Movie movie2 = movieBuilder("Dune", "Fantasy");
        List<Movie> movies = List.of(movie1, movie2);
        when(movieService.findByGenre("Fantasy")).thenReturn(entitiesToDtos(movies));
        mockMvc.perform(
                get("/movies/moviesByGenre?genre=Fantasy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(movies)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[1].name").value("Dune"));
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
                .andExpect(jsonPath("$.copyNumber").value(3));
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
