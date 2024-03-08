package com.example.movieClub.service;

import com.example.movieClub.exception.EntityNotFoundException;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.movieClub.model.dto.MovieCopyDtoMapper.dtoToEntity;
import static com.example.movieClub.model.dto.MovieCopyDtoMapper.entityToDto;

@Service
@AllArgsConstructor
public class MovieCopyService {

    private final MovieCopyRepository movieCopyRepository;
    private final MovieRepository movieRepository;

    public MovieCopyDto returnMovieCopy(Long id) {
        MovieCopy movieCopy = movieCopyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie copy with id = " + id + " not found"));
        movieCopy.setRentalDate(null);
        movieCopy.setUser(null);
        return MovieCopyDtoMapper.entityToDto(movieCopyRepository.save(movieCopy));
    }
}
