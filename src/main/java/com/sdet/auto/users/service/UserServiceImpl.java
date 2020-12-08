package com.sdet.auto.users.service;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.exceptions.UserNotFoundException;
import com.sdet.auto.users.model.User;
import com.sdet.auto.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserDto getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id); // Optional<User>, return will be given id info or empty()

        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found in User Repository");
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.get().getId());
        userDto.setUser_name(user.get().getUsername());
        userDto.setFirst_name(user.get().getFirstname());
        userDto.setLast_name(user.get().getLastname());
        userDto.setEmail(user.get().getEmail());
        userDto.setRole(user.get().getRole());

        return userDto;
    }

    @Override
    public UserDto getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);

        // if does not exists, throws an exception
        if(user == null){
            throw new UserNotFoundException("User not found in User Repository");
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUser_name(user.getUsername());
        userDto.setFirst_name(user.getFirstname());
        userDto.setLast_name(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());

        return userDto;
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

    @Override
    public void updateUserById(Long id, UserDto userDto) throws UserNotFoundException {
        // logic to check repository if user is present
        Optional<User> user = userRepository.findById(id); // Optional<User>, return will be given id info or empty()

        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found in User Repository");
        }
        // setting new User object from user returned from repository
        User saveUser = user.get();
        // check if userDto passed in has any changes for the following fields, else keep what they had before.
        if(userDto.getUser_name() != null && !userDto.getUser_name().isEmpty()) {
            saveUser.setUsername(userDto.getUser_name());
        }

        if(userDto.getFirst_name() != null && !userDto.getFirst_name().isEmpty()) {
            saveUser.setFirstname(userDto.getFirst_name());
        }

        if(userDto.getLast_name() != null && !userDto.getLast_name().isEmpty()) {
            saveUser.setLastname(userDto.getLast_name());
        }

        if(userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            saveUser.setEmail(userDto.getEmail());
        }

        if(userDto.getRole() != null && !userDto.getRole().isEmpty()) {
            saveUser.setRole(userDto.getRole());
        }

        userRepository.save(saveUser);
    }

    @Override
    public void deleteUserById(Long id) {
        // do not need logic to check if user already exist. typically with a delete, we try and think of it less
        // like being sure an operation is performed and think of it more like ensuring the intended state is achieved

        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            // do nothing.  Since the desired outcome is to delete a user, even if it does not exist we will
            // pass thru and not throw an error and let the controller return a 204
        }
    }
}