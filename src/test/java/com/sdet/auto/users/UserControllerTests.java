package com.sdet.auto.users;

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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
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
}