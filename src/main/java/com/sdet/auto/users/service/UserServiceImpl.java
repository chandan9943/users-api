package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.model.User;
import com.sdet.auto.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoCollection = new ArrayList<>();

        List<User> usersList =  userRepository.findAll();

        for (User user: usersList){
            UserDto userDto = new UserDto();
            // mapping db values to dto
            userDto.setId(user.getId());
            userDto.setUser_name(user.getUsername());
            userDto.setFirst_name(user.getFirstname());
            userDto.setLast_name(user.getLastname());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());

            userDtoCollection.add(userDto);
        }

        return userDtoCollection;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws UserExistsException {
        // logic to check repository if user is present
        User existingUser = userRepository.findByUsername(userDto.getUser_name());
        // if user exists, throws an exception
        if(existingUser != null){
            throw new UserExistsException("User already exists in User Repository");
        }

        User user = new User();
        user.setUsername(userDto.getUser_name());
        user.setFirstname(userDto.getFirst_name());
        user.setLastname(userDto.getLast_name());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        // save converted user to database
        User savedUser = userRepository.save(user);

        userDto.setId(savedUser.getId());
        // return user dto
        return userDto;
    }
}