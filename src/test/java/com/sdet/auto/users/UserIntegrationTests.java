package com.sdet.auto.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sdet.auto.users.dto.UserDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String path = "/users/v1";

    @Test
    public void user_tc0001_getAllUsers() {
        ResponseEntity<List> response = restTemplate.getForEntity(path, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void user_tc0002_createUser() {
        String td_UserName = "wonder.woman";
        String td_FirstName = "wonder";
        String td_LastName = "woman";
        String td_Email = "wonder.woman@gmail.com";
        String td_Role = "admin";
        String td_header = "/users/";

        UserDto entity = new UserDto(null, td_UserName, td_FirstName, td_LastName, td_Email, td_Role);
        ResponseEntity<UserDto> response = restTemplate.postForEntity(path, entity, UserDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        UserDto userDto = response.getBody();

        assertTrue(userDto.getId() > 0);
        assertEquals(td_UserName, userDto.getUser_name());
        assertEquals(td_FirstName, userDto.getFirst_name());
        assertEquals(td_LastName, userDto.getLast_name());
        assertEquals(td_Email, userDto.getEmail());
        assertEquals(td_Role, userDto.getRole());

        // get header from response
        HttpHeaders header = response.getHeaders();
        // assert expected header matches actual
        assertEquals(td_header + userDto.getId(), header.getLocation().getPath());
    }

    @Test
    public void user_tc0003_createUser_exception() throws JsonProcessingException {
        String td_UserName = "thor.odinson";
        String td_Error = "Bad Request";
//        String td_Message = "User already exists in User Repository";
        String td_path = "/users/v1";

        UserDto entity = new UserDto(null, td_UserName, "", "", "", "");
        ResponseEntity<String> response = restTemplate.postForEntity(path, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // getting the response body
        String body = response.getBody();

        // get fields from JSON using Jackson Object Mapper
        final ObjectNode node = new ObjectMapper().readValue(body, ObjectNode.class);

        // assert expected vs actual
        assertEquals(td_Error, node.get("error").asText());
//        assertEquals(td_Message, node.get("message").asText());
        assertEquals(td_path, node.get("path").asText());
    }
}