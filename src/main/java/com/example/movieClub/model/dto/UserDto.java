package com.example.movieClub.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private String name;
    private String lastName;
    private String password;
    private String email;
}
