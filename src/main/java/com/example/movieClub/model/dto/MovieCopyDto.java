package com.example.movieClub.model.dto;

import com.example.movieClub.model.Movie;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class MovieCopyDto {
    private int copyNumber;
    private LocalDate rentalDate;
    private MovieDto movie;
}
