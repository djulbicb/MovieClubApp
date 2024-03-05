package com.example.movieClub.service;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDtoMapper;
import com.example.movieClub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.movieClub.model.dto.UserDtoMapper.entityToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateUser() {
        User user = User.builder().name("Sam").build();
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.createUser(entityToDto(user))).isEqualTo(entityToDto(user));
    }
}