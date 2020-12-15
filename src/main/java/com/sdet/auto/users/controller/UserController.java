package com.sdet.auto.users.controller;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.exceptions.UserNotFoundException;
import com.sdet.auto.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("users/v1")
@Api(tags = "User RESTful Api", value = "UserController")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "retrieves a list of users")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "get a user by id")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @ApiOperation(value = "find a user by username")
    @GetMapping("/byusername/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @ApiOperation(value = "creates a user")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@ApiParam("User fields to create a new user")
                                                  @Valid @RequestBody UserDto user, UriComponentsBuilder builder) {
        try {
            UserDto returnDto = userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/v1/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(returnDto, headers, HttpStatus.CREATED);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @ApiOperation(value = "update a user")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserById(@PathVariable("id") @Min(1) Long id, @RequestBody UserDto user) {
        try {
            userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @ApiOperation(value = "delete a user")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") @Min(1) Long id) {
        userService.deleteUserById(id);
    }
}