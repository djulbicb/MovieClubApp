package com.example.movieClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movie_copies")

public class MovieCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int copyNumber;
    private LocalDate rentalDate;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    //kada se fecuje kopija fecuje se i njen film
    //film i kopija ne mogu oba biti eager, jedan mora biti lazy, ili oba mogu biti lazy????
    private Movie movie;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;
}
