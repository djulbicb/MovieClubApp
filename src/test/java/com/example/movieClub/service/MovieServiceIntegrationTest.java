package com.example.movieClub.service;

import com.example.movieClub.model.Movie;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.model.dto.MovieDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
    }

    @Test
    void shouldGetMovies() {
        List<MovieDto> movieDtos = movieService.getMovies();
        assertThat(movieDtos.size()).isEqualTo(2);
        assertThat(movieDtos.get(0).getName()).isEqualTo("Dune");
    }

    @Test
    void shouldCreateMovie() {
        Movie movie = Movie.builder().name("Fight Club").year(2005).build();
        MovieDto movieDto = MovieDtoMapper.entityToDto(movie);
        movieService.createMovie(movieDto);
        List<MovieDto> movieDtos = movieService.getMovies();
        assertThat(movieDtos.size()).isEqualTo(3);
        assertThat(movieDtos.get(2).getName()).isEqualTo(movie.getName());
    }

    @Test
    void shouldDeleteMovieById() {
        movieService.deleteMovieById(1l);
        List<MovieDto> movieDtos = movieService.getMovies();
        assertThat(movieDtos.size()).isEqualTo(2);
        assertThat(movieDtos.get(0).getName()).isEqualTo("Avatar");
    }
}
