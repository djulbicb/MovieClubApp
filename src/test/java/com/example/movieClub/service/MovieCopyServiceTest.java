package com.example.movieClub.service;

import com.example.movieClub.MovieTestData;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.movieClub.MovieTestData.movieBuilder;
import static com.example.movieClub.MovieTestData.movieCopyBuilder;
import static com.example.movieClub.model.dto.MovieCopyDtoMapper.entityToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieCopyServiceTest {
    @Mock
    private MovieCopyRepository movieCopyRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieCopyService movieCopyService;

    @Test
    public void shouldCreateMovieCopy() {
        Movie movie = Movie.builder().name("Dune").genre("Fantasy").build();
        MovieCopy movieCopy = movieCopyBuilder(20, movie);
        when(movieRepository.findByNameAndGenre("Dune", "Fantasy")).thenReturn(List.of(movie));
        when(movieCopyRepository.save(movieCopy)).thenReturn(movieCopy);
        assertThat(movieCopyService.createMovieCopy(entityToDto(movieCopy))).isEqualTo(entityToDto(movieCopy));
    }

    @Test
    public void shouldReturnMovieCopy() {
        MovieCopy movieCopy = movieCopyBuilder(20, movieBuilder("Dune", "Fantasy"));
        when(movieCopyRepository.findById(1L)).thenReturn(Optional.ofNullable(movieCopy));
        when(movieCopyRepository.save(movieCopy)).thenReturn(movieCopy);
        assertThat(movieCopyService.returnMovieCopy(1L)).isEqualTo(entityToDto(movieCopy));
    }
}