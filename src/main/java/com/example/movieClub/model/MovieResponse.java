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
// Vidim da ti se response klase zovu Response i Dto... Izaberi jedno ili drugo, nemoj oba
public class MovieResponse {

    private List<MovieDto> movies;
    private long totalCount;
}
