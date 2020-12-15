package com.sdet.auto.users;

import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.exceptions.UserExistsException;
import com.sdet.auto.users.exceptions.UserNotFoundException;
import com.sdet.auto.users.model.User;
import com.sdet.auto.users.repository.UserRepository;
import com.sdet.auto.users.service.UserService;
import com.sdet.auto.users.service.UserServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTests {
    // to check the Service class, we just need an instance of the Service class created and available as a bean so we
    // can @Autowire it with our test case. This can be achieved using the @TestConfiguration annotation (below)
    @TestConfiguration
    static class UserServiceImplTestsContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    // this creates a Mock for the UserRepository which can be used to bypass the call to the actual UserRepository
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() throws UserExistsException {

        Long td_id1 = 111L;
        String td_userName1 = "td_userName1";
        String td_firstName1 = "td_firstName1";
        String td_lastName1 = "td_lastName1";
        String td_email1 = "td_email1";
        String td_role1 = "td_role1";

        Long td_id2 = 222L;
        String td_userName2 = "td_userName2";
        String td_firstName2 = "td_firstName2";
        String td_lastName2 = "td_lastName2";
        String td_email2 = "td_email2";
        String td_role2 = "td_role2";

        Long td_id3 = 333L;
        String td_userName3 = "td_userName3";
        String td_firstName3 = "td_firstName3";
        String td_lastName3 = "td_lastName3";
        String td_email3 = "td_email3";
        String td_role3 = "td_role3";

        User user1 = new User(td_id1, td_userName1, td_firstName1, td_lastName1, td_email1, td_role1);
        User user2 = new User(td_id2, td_userName2, td_firstName2, td_lastName2, td_email2, td_role2);
        User user3 = new User(td_id3, td_userName3, td_firstName3, td_lastName3, td_email3, td_role3);

        List<User> td_users = Arrays.asList(user1, user2, user3);

        // list out mocks scenarios below.
        Mockito.when(userRepository.findAll()).thenReturn(td_users);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user3);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(user1));
    }

    @Test
    public void user_service_tc0001_getAllUsers() {
        int td_array_size = 3;
        int user1 = 0;
        Long td_id1 = 111L;
        String td_userName1 = "td_userName1";
        String td_firstName1 = "td_firstName1";
        String td_lastName1 = "td_lastName1";
        String td_email1 = "td_email1";
        String td_role1 = "td_role1";

        List<UserDto> users = userService.getAllUsers();

        assertEquals(users.size(), td_array_size);
        assertEquals(users.get(user1).getId(), td_id1);
        assertEquals(users.get(user1).getUser_name(), td_userName1);
        assertEquals(users.get(user1).getFirst_name(), td_firstName1);
        assertEquals(users.get(user1).getLast_name(), td_lastName1);
        assertEquals(users.get(user1).getEmail(), td_email1);
        assertEquals(users.get(user1).getRole(), td_role1);
    }

    @Test
    public void user_service_tc0002_createUser() throws UserExistsException {
        Long td_id3 = 333L;
        String td_userName3 = "td_userName3";
        String td_firstName3 = "td_firstName3";
        String td_lastName3 = "td_lastName3";
        String td_email3 = "td_email3";
        String td_role3 = "td_role3";

        UserDto inputUser = new UserDto(td_id3, td_userName3, td_firstName3, td_lastName3, td_email3, td_role3);

        UserDto returnUser = userService.createUser(inputUser);

        assertEquals(returnUser.getId(), td_id3);
        assertEquals(returnUser.getUser_name(), td_userName3);
        assertEquals(returnUser.getFirst_name(), td_firstName3);
        assertEquals(returnUser.getLast_name(), td_lastName3);
        assertEquals(returnUser.getEmail(), td_email3);
        assertEquals(returnUser.getRole(), td_role3);
    }

    @Test
    public void user_service_tc0003_createUser_exception() {
        String td_userName = "td_userName";
        String td_error_message = "User already exists in User Repository";

        UserDto userDto = new UserDto(null, td_userName, "", "", "", "");

        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(new User());

        try {
            userService.createUser(userDto);
        } catch (UserExistsException ex) {
            assertEquals(td_error_message, ex.getMessage());
        }
    }

    @Test
    public void user_service_tc0004_getUserById() throws UserNotFoundException {
        Long td_id1 = 111L;
        String td_userName1 = "td_userName1";
        String td_firstName1 = "td_firstName1";
        String td_lastName1 = "td_lastName1";
        String td_email1 = "td_email1";
        String td_role1 = "td_role1";

        UserDto users = userService.getUserById(td_id1);

        assertEquals(users.getId(), td_id1);
        assertEquals(users.getUser_name(), td_userName1);
        assertEquals(users.getFirst_name(), td_firstName1);
        assertEquals(users.getLast_name(), td_lastName1);
        assertEquals(users.getEmail(), td_email1);
        assertEquals(users.getRole(), td_role1);
    }

    @Test
    public void user_service_tc0005_getUserById_exception() {
        Long td_id1 = 999L;
        String td_error_message = "User not found in User Repository";

        try {
            userService.getUserById(td_id1);
        } catch (UserNotFoundException ex) {
            assertEquals(td_error_message, ex.getMessage());
        }
    }

    @Test
    public void user_service_tc0006_getUserByUsername() throws UserNotFoundException {
        Long td_id2 = 222L;
        String td_userName2 = "td_userName2";
        String td_firstName2 = "td_firstName2";
        String td_lastName2 = "td_lastName2";
        String td_email2 = "td_email2";
        String td_role2 = "td_role2";

        User user2 = new User(td_id2, td_userName2, td_firstName2, td_lastName2, td_email2, td_role2);

        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user2);

        UserDto users = userService.getUserByUsername(td_userName2);

        assertEquals(users.getId(), td_id2);
        assertEquals(users.getUser_name(), td_userName2);
        assertEquals(users.getFirst_name(), td_firstName2);
        assertEquals(users.getLast_name(), td_lastName2);
        assertEquals(users.getEmail(), td_email2);
        assertEquals(users.getRole(), td_role2);
    }

    @Test
    public void user_service_tc0007_getUserByUsername_exception() {
        String td_userName2 = "td_userName2";
        String td_error_message = "User not found in User Repository";

        try {
            userService.getUserByUsername(td_userName2);
        } catch (UserNotFoundException ex) {
            assertEquals(td_error_message, ex.getMessage());
        }
    }

    @Test
    public void user_service_tc0008_updateUserById() throws UserNotFoundException {
        // since Mockito.when(userRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(user1));
        // is defined up top, as long as you set an id below it will get a mocked user within updateUserById method
        userService.updateUserById(1L, new UserDto());
        // checking if userRepository.save() was invoked within the updateUserById method
        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void user_service_tc0009_updateUserById_exception() throws UserNotFoundException {
        String td_error_message = "User not found in User Repository";
        try {
            // passing a null to the function below, since its not a long, will cause a user not found
            userService.updateUserById(null, new UserDto());
        } catch (UserNotFoundException ex) {
            assertEquals(td_error_message, ex.getMessage());
        }
    }
}