package com.example.movieClub.model.dto;

import com.example.movieClub.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static User dtoToEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword()).build();
    }

    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword()).build();
    }

    public static List<UserDto> entitiesToDtos(List<User> users) {
        List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }
}
