package com.example.movieClub.service;

import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmailReminderService {

    private final MovieCopyRepository movieCopyRepository;


}
