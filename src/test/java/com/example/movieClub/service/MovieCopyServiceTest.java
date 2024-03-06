package com.example.movieClub.service;

import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.repository.MovieCopyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.movieClub.MovieTestData.movieBuilder;
import static com.example.movieClub.MovieTestData.movieCopyBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieCopyServiceTest {
    @Mock
    private MovieCopyRepository movieCopyRepository;

    @InjectMocks
    private MovieCopyService movieCopyService;

    @Test
    public void shouldReturnMovieCopy() {
        MovieCopy movieCopy = movieCopyBuilder(20, movieBuilder("Dune", "Fantasy"));
        movieCopy.setUser(User.builder().name("Isidora").build());
        movieCopy.setRentalDate(LocalDate.now());
        when(movieCopyRepository.findById(1L)).thenReturn(Optional.ofNullable(movieCopy));
        when(movieCopyRepository.save(movieCopy)).thenReturn(movieCopy);
        assertThat(movieCopyService.returnMovieCopy(1L).getRentalDate()).isNull();
    }
}