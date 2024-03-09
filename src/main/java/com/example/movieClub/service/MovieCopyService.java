package com.example.movieClub.service;

import com.example.movieClub.exception.EntityNotFoundException;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.model.dto.UserDtoMapper;
import com.example.movieClub.repository.MovieCopyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MovieCopyService {

    private final MovieCopyRepository movieCopyRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue emailQueue;

    public MovieCopyDto returnMovieCopy(Long id) {
        MovieCopy movieCopy = movieCopyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie copy with id = " + id + " not found"));
        movieCopy.setRentalDate(null);
        movieCopy.setUser(null);
        return MovieCopyDtoMapper.entityToDto(movieCopyRepository.save(movieCopy));
    }

    public List<UserDto> getUsersWithOverdueRentals() {
        LocalDate latestDate = LocalDate.now().minusMonths(1);
        List<MovieCopy> movieCopies = movieCopyRepository.findCopiesWithOverdueRentalDate(latestDate);
        List<User> usersWithOverdueRentals = movieCopies.stream().map(movieCopy -> movieCopy.getUser()).collect(Collectors.toList());
        return UserDtoMapper.entitiesToDtos(usersWithOverdueRentals);
    }

    @Scheduled(fixedRate = 1440 * 60 * 1000) // triggering every 24h
    public void sendOverdueRentalUsersToQueue() {
        List<UserDto> userDtos = getUsersWithOverdueRentals();
        List<String> emails = userDtos.stream().map(userDto -> userDto.getEmail()).toList();
        for (String email : emails) {
            rabbitTemplate.convertAndSend(emailQueue.getName(), email);
        }
    }
}
