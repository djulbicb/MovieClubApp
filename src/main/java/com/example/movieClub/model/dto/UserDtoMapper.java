package com.example.movieClub.model.dto;

import com.example.movieClub.model.User;

public class UserDtoMapper {

    public static User dtoToEntity(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword()).build();
    }
}
