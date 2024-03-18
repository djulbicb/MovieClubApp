package com.example.movieClub.model.dto;

import com.example.movieClub.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDtoMapper {
    public static MovieDto entityToDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .imageUrl(movie.getImageUrl())
                .year(movie.getYear()).build();
    }

    public static List<MovieDto> entitiesToDtos(List<Movie> movies) {
        List<MovieDto> movieDtos = movies.stream().map(m -> entityToDto(m)).collect(Collectors.toList());
        return movieDtos;
    }

    public static Movie dtoToEntity(MovieDto movieDto) {
        return Movie.builder().name(movieDto.getName())
                .genre(movieDto.getGenre())
                .year(movieDto.getYear())
                .description(movieDto.getDescription())
                .imageUrl(movieDto.getImageUrl()).build();
    }
}
