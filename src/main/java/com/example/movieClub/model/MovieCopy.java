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

// Nemoj da koristis rec copy u naslovu klase.
// Copy znaci gomilu stvari i nije dovoljno fokusirana rec
// MovieRental ili tako nesto je bolje
public class MovieCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int copyNumber;
    private LocalDate rentalDate;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Movie movie;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;
    @Version
    private int version;
}
