package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;
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
}