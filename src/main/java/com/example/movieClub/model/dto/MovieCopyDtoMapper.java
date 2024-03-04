package com.example.movieClub.model.dto;

import com.example.movieClub.model.MovieCopy;

import java.util.List;
import java.util.stream.Collectors;

public class MovieCopyDtoMapper {
    public static MovieCopyDto entityToDto(MovieCopy movieCopy) {
        return MovieCopyDto.builder()
                .copyNumber(movieCopy.getCopyNumber())
                .movie(MovieDtoMapper.entityToDto(movieCopy.getMovie()))
                .rentalDate(movieCopy.getRentalDate())
                .build();
    }

    public static MovieCopy dtoToEntity(MovieCopyDto movieCopyDto) {
        return MovieCopy.builder()
                .copyNumber(movieCopyDto.getCopyNumber())
                .rentalDate(movieCopyDto.getRentalDate())
                .movie(MovieDtoMapper.dtoToEntity(movieCopyDto.getMovie()))
                .build();
    }

    public static List<MovieCopyDto> entitiesToDto(List<MovieCopy> movieCopies) {
        return movieCopies.stream().map(movieCopy -> entityToDto(movieCopy)).collect(Collectors.toList());
    }
}
