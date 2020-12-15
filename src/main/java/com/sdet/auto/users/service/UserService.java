package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user) throws UserExistsException;
}