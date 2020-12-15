package com.sdet.auto.users.controller;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto, UriComponentsBuilder builder) {
        try {
            UserDto returnDto = userService.createUser(userDto);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri());
            return new ResponseEntity<>(returnDto, headers, HttpStatus.CREATED);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}