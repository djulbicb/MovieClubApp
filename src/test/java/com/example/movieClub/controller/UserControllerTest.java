package com.example.movieClub.controller;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.model.dto.UserDtoMapper;
import com.example.movieClub.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.movieClub.MovieTestData.userBuilder;
import static com.example.movieClub.model.dto.UserDtoMapper.entitiesToDtos;
import static com.example.movieClub.model.dto.UserDtoMapper.entityToDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldCreateUser() throws Exception {
        User user = userBuilder("Dora", "dora@gmail.com");
        UserDto userDto = UserDtoMapper.entityToDto(user);
        when(userService.createUser(userDto)).thenReturn(userDto);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dora"));
    }

    @Test
    void shouldGetAll() throws Exception {
        User user1 = userBuilder("Dora", "dora@gmail.com");
        User user2 = userBuilder("Mina", "mina@gmail.com");
        List<User> users = List.of(user1, user2);
        when(userService.getUsers()).thenReturn(entitiesToDtos(users));
        mockMvc.perform(
                get("/user/allUsers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(entitiesToDtos(users))))
                .andExpect(jsonPath("$[1].name").value("Mina"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetById() throws Exception {
        User user = userBuilder("Dora", "dora@gmail.com");
        when(userService.getUserById(1L)).thenReturn(entityToDto(user));
        mockMvc.perform(
                get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(entityToDto(user))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dora"));
    }

    @Test
    void shouldUpdate() throws Exception {
        User user = userBuilder("Dora", "dora@gmail.com");
        UserDto userDto = UserDtoMapper.entityToDto(user);
        when(userService.updateUser(userDto, 1L)).thenReturn(userDto);
        mockMvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dora"));
        verify(userService, Mockito.times(1)).updateUser(userDto, 1L);
    }

    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(
                delete("/user/1")).andExpect(status().isOk());
        verify(userService, Mockito.times(1)).deleteUserById(1L);
    }

    private String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
