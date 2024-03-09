package com.example.movieClub;

import com.example.movieClub.model.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
public class MovieClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieClubApplication.class, args);
	}
}
