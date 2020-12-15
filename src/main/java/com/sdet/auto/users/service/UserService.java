package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws UserNotFoundException;

    UserDto getUserByUsername(String username) throws UserNotFoundException;

    UserDto createUser(UserDto user) throws UserExistsException;

    void updateUserById(Long id, UserDto userDto) throws UserNotFoundException;
}