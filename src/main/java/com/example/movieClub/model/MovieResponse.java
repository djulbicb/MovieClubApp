package com.example.movieClub.model;

import com.example.movieClub.model.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {

    private List<MovieDto> movies;
    private long totalCount;
}
