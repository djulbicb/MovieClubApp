package com.example.movieClub.controller;

import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieDto;
import com.example.movieClub.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/allMovies")
    public List<MovieDto> getAll() {
        return movieService.getMovies();
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

}
