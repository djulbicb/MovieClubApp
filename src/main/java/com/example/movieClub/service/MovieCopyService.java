package com.example.movieClub.service;

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


    public MovieCopyDto createMovieCopy(MovieCopyDto movieCopyDto) {
        MovieCopy copy = dtoToEntity(movieCopyDto);
        String movieName = movieCopyDto.getMovie().getName();
        String movieGenre = movieCopyDto.getMovie().getGenre();
        Movie movie = movieRepository.findByNameAndGenre(movieName, movieGenre).stream().findFirst().orElseThrow();
        copy.setMovie(movie);
        movieCopyRepository.save(copy);
        return MovieCopyDtoMapper.entityToDto(copy);
    }

    public MovieCopyDto returnMovieCopy(Long id) {
        MovieCopy movieCopy = movieCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("There is no copy with id " + id));
        movieCopy.setRentalDate(null);
        movieCopyRepository.save(movieCopy);
        return MovieCopyDtoMapper.entityToDto(movieCopy);
    }
}
