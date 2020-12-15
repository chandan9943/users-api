package com.sdet.auto.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdet.auto.users.controller.UserController;
import com.sdet.auto.users.dto.UserDto;
import com.sdet.auto.users.service.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final String path = "/users/v1";

    @Test
    public void user_controller_tc0001_getAllUsers() throws Exception {
        Long td_id = 111L;
        String td_userName = "td_userName1";
        String td_firstName = "td_firstName1";
        String td_lastName = "td_lastName1";
        String td_email = "td_email1";
        String td_role = "td_role1";

        UserDto userDto = new UserDto(td_id, td_userName, td_firstName, td_lastName, td_email, td_role);

        List<UserDto> allUsers = Arrays.asList(userDto);
        // when method is called, return mock
        given(userService.getAllUsers()).willReturn(allUsers);

        mockMvc.perform(get(path)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(td_id))
                .andExpect(jsonPath("$[0].user_name").value(td_userName))
                .andExpect(jsonPath("$[0].first_name").value(td_firstName))
                .andExpect(jsonPath("$[0].last_name").value(td_lastName))
                .andExpect(jsonPath("$[0].email").value(td_email))
                .andExpect(jsonPath("$[0].role").value(td_role));
    }

    @Test
    public void user_controller_tc0002_createUser() throws Exception {
        Long td_id = 222L;
        String td_userName = "td_userName2";
        String td_firstName = "td_firstName2";
        String td_lastName = "td_lastName2";
        String td_email = "td_email2";
        String td_role = "td_role2";

        UserDto userDto = new UserDto(td_id, td_userName, td_firstName, td_lastName, td_email, td_role);

        ObjectMapper objectMapper = new ObjectMapper();

        String userAsString = objectMapper.writeValueAsString(userDto);

        // when method is called, return mock
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform( MockMvcRequestBuilders
                .post(path)
                .content(userAsString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(jsonPath("$.user_name").value(td_userName))
                .andExpect(jsonPath("$.first_name").value(td_firstName))
                .andExpect(jsonPath("$.last_name").value(td_lastName))
                .andExpect(jsonPath("$.email").value(td_email))
                .andExpect(jsonPath("$.role").value(td_role));
    }

    @Test
    public void user_controller_tc0003_getUserById() throws Exception {
        Long td_id = 111L;
        String td_userName = "td_userName1";
        String td_firstName = "td_firstName1";
        String td_lastName = "td_lastName1";
        String td_email = "td_email1";
        String td_role = "td_role1";
        String td_ssn = "td_ssn1";

        UserDto user = new UserDto(td_id, td_userName, td_firstName, td_lastName, td_email, td_role);

        given(userService.getUserById(any(Long.class))).willReturn(user);

        mockMvc.perform(get("/users/v1/111")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(td_id))
                .andExpect(jsonPath("$.user_name").value(td_userName))
                .andExpect(jsonPath("$.first_name").value(td_firstName))
                .andExpect(jsonPath("$.last_name").value(td_lastName))
                .andExpect(jsonPath("$.email").value(td_email))
                .andExpect(jsonPath("$.role").value(td_role));
    }

    @Test
    public void user_controller_tc0004_getUserByUsername() throws Exception {
        Long td_id = 111L;
        String td_userName = "td_userName";
        String td_firstName = "td_firstName";
        String td_lastName = "td_lastName";
        String td_email = "td_email";
        String td_role = "td_role";

        UserDto user = new UserDto(td_id,td_userName, td_firstName, td_lastName, td_email, td_role);

        given(userService.getUserByUsername(any(String.class))).willReturn(user);

        mockMvc.perform(get("/users/v1/byusername/" + td_userName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(td_id))
                .andExpect(jsonPath("$.user_name").value(td_userName))
                .andExpect(jsonPath("$.first_name").value(td_firstName))
                .andExpect(jsonPath("$.last_name").value(td_lastName))
                .andExpect(jsonPath("$.email").value(td_email))
                .andExpect(jsonPath("$.role").value(td_role));
    }
}