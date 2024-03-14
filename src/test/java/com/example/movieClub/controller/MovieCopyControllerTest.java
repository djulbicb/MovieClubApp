package com.example.movieClub.controller;

import com.example.movieClub.MovieTestData;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.service.MovieCopyService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static com.example.movieClub.model.dto.MovieCopyDtoMapper.entityToDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MovieCopyControllerTest {

    @Mock
    private MovieCopyService movieCopyService;

    @InjectMocks
    private MovieCopyController movieCopyController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(movieCopyController).build();
    }

    @Test
    void shouldReturnMovieCopy() throws Exception {
        Movie movie = MovieTestData.movieBuilder("Harry Potter", "Fantasy");
        MovieCopy movieCopy = MovieTestData.movieCopyBuilder(3, movie);
        when(movieCopyService.returnMovieCopy(1L)).thenReturn(entityToDto(movieCopy));
        mockMvc.perform(put("/movieCopies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(entityToDto(movieCopy))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.copyNumber").value(3));
        verify(movieCopyService, Mockito.times(1)).returnMovieCopy(1L);
    }

    private String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
