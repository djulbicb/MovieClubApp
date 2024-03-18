package com.example.movieClub.model.dto;

import com.example.movieClub.model.MovieCopy;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieDto {
    private Long id;
    private String name;
    private String genre;
    private String description;
    private String imageUrl;
    private int year;
}
