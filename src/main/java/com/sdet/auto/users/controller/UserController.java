package com.sdet.auto.users.controller;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}