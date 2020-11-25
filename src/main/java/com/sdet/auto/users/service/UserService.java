package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
}