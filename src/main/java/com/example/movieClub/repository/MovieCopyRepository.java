package com.example.movieClub.repository;

import com.example.movieClub.model.MovieCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCopyRepository extends JpaRepository<MovieCopy, Long> {

}
