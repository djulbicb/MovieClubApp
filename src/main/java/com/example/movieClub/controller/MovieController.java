package com.example.movieClub.controller;

import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/allMovies")
    public ResponseEntity<Map<String, Object>> getAll(@PageableDefault(page = 1, size = 2) Pageable pageable) {//        return movieService.getMovies(pageable);
        List<MovieDto> movies = movieService.getMovies(pageable);
        long totalCount = movieService.getMovies().size();

        Map<String, Object> response = new HashMap<>();
        response.put("movies", movies);
        response.put("totalCount", totalCount);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public MovieDto getById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }

    @PostMapping
    public MovieDto create(@RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }

    @PutMapping("/{id}")
    public MovieDto update(@RequestBody MovieDto movieDto, @PathVariable Long id) {
        return movieService.updateMovie(movieDto, id);
    }

    @GetMapping("/moviesByGenre")
    public List<MovieDto> getByGenre(@RequestParam String genre) {
        return movieService.findByGenre(genre);
    }

    @PutMapping("/rent/{id}")
    public MovieCopyDto rentMovieCopy(@PathVariable Long id) {
        return movieService.rentMovieCopy(id);
    }

    @GetMapping("/availableCopies/{id}")
    public int getNumOfAvailableCopies(@PathVariable Long id) {
        return movieService.getNumOfAvailableCopies(id);
    }
}
