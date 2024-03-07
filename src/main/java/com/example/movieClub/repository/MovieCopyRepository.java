package com.example.movieClub.repository;

import com.example.movieClub.model.MovieCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieCopyRepository extends JpaRepository<MovieCopy, Long> {

    @Query("SELECT copy from MovieCopy copy WHERE copy.rentalDate IS NOT NULL AND " +
            "copy.rentalDate <= :date")
    public List<MovieCopy> findCopiesWithOverdueRentalDate(@Param("date") LocalDate date);
}
