package com.example.movieClub.service;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.model.dto.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.movieClub.MovieTestData.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
    }

    @Test
    void shouldCreateUser() {
        User user = userBuilder("Mica", "mica@gmail.com");
        UserDto userDto = UserDtoMapper.entityToDto(user);
        userService.createUser(userDto);
        List<UserDto> userDtoList = userService.getUsers();
        System.out.println(userDtoList);
        assertThat(userDtoList.size()).isEqualTo(3);
        assertThat(userDtoList).contains(userDto);
    }

    @Test
    void shouldDeleteUser() {
        userService.deleteUserById(1l);
        List<UserDto> userDtoList = userService.getUsers();
        assertThat(userDtoList.size()).isEqualTo(2);
        assertThat(userDtoList.stream().map(userDto -> userDto.getEmail()).collect(Collectors.toList())).doesNotContain("tea@gmail.com");
    }
}
