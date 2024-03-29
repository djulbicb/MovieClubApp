package com.example.movieClub.service;

import com.example.movieClub.exception.EntityNotFoundException;
import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.movieClub.model.dto.UserDtoMapper.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User findLoggedInUser() {
        return findUserById(3L);
    } // todo: implement authorization

    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        userRepository.save(user);
        return userDto;
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return entitiesToDtos(users);
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        User user = findUserById(id);
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return entityToDto(userRepository.save(user));
    }

    public UserDto getUserById(Long id){
        return entityToDto(findUserById(id));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found"));
    }
}
