package com.example.movieClub.service;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.model.dto.UserDtoMapper;
import com.example.movieClub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User findLoggedInUser() {
        return User.builder()
                .name("Dora")
                .lastName("Bra")
                .email("dora@gmail.com")
                .build();
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserDtoMapper.dtoToEntity(userDto);
        userRepository.save(user);
        return userDto;
    }
}
