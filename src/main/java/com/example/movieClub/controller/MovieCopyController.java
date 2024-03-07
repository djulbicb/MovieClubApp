package com.example.movieClub.controller;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.service.MovieCopyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/movieCopies")
@RestController
public class MovieCopyController {

    private final MovieCopyService movieCopyService;

    @PutMapping("/{id}")
    public MovieCopyDto returnMovieCopy(@PathVariable Long id) {
        return movieCopyService.returnMovieCopy(id);
    }

    @GetMapping("/overdued")
    public List<UserDto> getOverdued() {
        return movieCopyService.getUsersWithOverdueRentals();
    }

    @PostMapping("/sendEmail")
    public void sendEmail() {
        movieCopyService.sendReminderEmails();
    }
}
