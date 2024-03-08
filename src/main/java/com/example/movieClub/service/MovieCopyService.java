package com.example.movieClub.service;

import com.example.movieClub.exception.EntityNotFoundException;
import com.example.movieClub.model.Movie;
import com.example.movieClub.model.MovieCopy;
import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.MovieCopyDto;
import com.example.movieClub.model.dto.MovieCopyDtoMapper;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.model.dto.UserDtoMapper;
import com.example.movieClub.repository.MovieCopyRepository;
import com.example.movieClub.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.example.movieClub.model.dto.MovieCopyDtoMapper.dtoToEntity;
import static com.example.movieClub.model.dto.MovieCopyDtoMapper.entityToDto;

@Service
@AllArgsConstructor
public class MovieCopyService {

    private final MovieCopyRepository movieCopyRepository;
    private final MovieRepository movieRepository;

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

    public void sendReminderEmails() {
        List<UserDto> overdueUsers = getUsersWithOverdueRentals();

        for (UserDto user : overdueUsers) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername("adore.nails.ns@gmail.com");
            mailSender.setPassword("vjzp iqoz xxou ibnx");

            Properties properties = mailSender.getJavaMailProperties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.debug", "true");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("adore.nails.ns@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Please return overdue movie rental");
            message.setText("Your movie rental is Overdue, lease return it at your earliest convenience.");

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        }
    }
}
