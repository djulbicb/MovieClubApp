package com.example.movieClub.repository;

import com.example.movieClub.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //metoda za cuvanje u bazu
}
