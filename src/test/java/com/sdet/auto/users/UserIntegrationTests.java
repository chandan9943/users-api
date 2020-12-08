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

    @Test
    public void user_tc0004_getByUserId() {
        String td_UserId = "101";
        String td_UserName = "darth.vader";
        String td_FirstName = "darth";
        String td_LastName = "vader";
        String td_Email = "darth.vader@gmail.com";
        String td_Role = "villain";

        ResponseEntity<UserDto> response = restTemplate.getForEntity(path + "/" + td_UserId, UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDto userResponse = response.getBody();

        assertEquals(td_UserId, userResponse.getId().toString());
        assertEquals(td_UserName, userResponse.getUser_name());
        assertEquals(td_FirstName, userResponse.getFirst_name());
        assertEquals(td_LastName, userResponse.getLast_name());
        assertEquals(td_Email, userResponse.getEmail());
        assertEquals(td_Role, userResponse.getRole());
    }

    @Test
    public void user_tc0005_getByUserId_exception() throws JsonProcessingException {
        String td_UserId = "1001";
        String td_Error = "Not Found";
//        String td_Message = "User not found in User Repository";
        String td_path = "/users/v1/" + td_UserId;
        // since response will not be a user object, casting the response as a String so we can map to an object
        ResponseEntity<String> response = restTemplate.getForEntity(path + "/" + td_UserId, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // getting the response body
        String body = response.getBody();
        // get fields from JSON using Jackson Object Mapper
        final ObjectNode node = new ObjectMapper().readValue(body, ObjectNode.class);
        // assert expected vs actual
        assertEquals(td_Error, node.get("error").asText());
//        assertEquals(td_Message, node.get("message").asText());
        assertEquals(td_path, node.get("path").asText());
    }

    @Test
    public void user_tc0006_getByUsername() {
        String td_id = "103";
        String td_UserName = "thor.odinson";
        String td_FirstName = "thor";
        String td_LastName = "odinson";
        String td_Email = "thor@gmail.com";
        String td_Role = "hero";

        ResponseEntity<UserDto> response = restTemplate.getForEntity(path + "/byusername/" + td_UserName, UserDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDto user = response.getBody();

        assertEquals(td_id, user.getId().toString());
        assertEquals(td_UserName, user.getUser_name());
        assertEquals(td_FirstName, user.getFirst_name());
        assertEquals(td_LastName, user.getLast_name());
        assertEquals(td_Email, user.getEmail());
        assertEquals(td_Role, user.getRole());
    }

    @Test
    public void user_tc0007_getByUsername_exception() throws JsonProcessingException {
        String td_userName = "bad.username";
        String td_pathDetails = "/users/v1/byusername/" + td_userName;
        String td_error = "Not Found";

        // since response will not be a user object, casting the response as a String so we can map to an object
        ResponseEntity<String> response = restTemplate.getForEntity(path + "/byusername/" + td_userName, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // getting the response body
        String body = response.getBody();
        // get fields from JSON using Jackson Object Mapper
        final ObjectNode node = new ObjectMapper().readValue(body, ObjectNode.class);

        // assert expected vs actual
        assertEquals(td_pathDetails, node.get("path").asText());
        assertEquals(td_error, node.get("error").asText());
    }

    @Test
    public void user_tc0008_updateUserById() {
        String td_Id = "101";
        String td_UserName = "darth.vader_updated";
        String td_FirstName = "darth_updated";
        String td_LastName = "vader_updated";
        String td_Email = "darth.vader_updated@gmail.com";
        String td_Role = "admin_updated";

        // making a get to get a user record
        ResponseEntity<UserDto> initResponse = restTemplate.getForEntity(path + "/" + td_Id, UserDto.class);

        UserDto initUser = initResponse.getBody();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> entity = new HttpEntity<>(initUser, headers);
        // edit user entity with updated test data values
        entity.getBody().setUser_name(td_UserName);
        entity.getBody().setFirst_name(td_FirstName);
        entity.getBody().setLast_name(td_LastName);
        entity.getBody().setEmail(td_Email);
        entity.getBody().setRole(td_Role);

        // make a put call to edit the record using an api put request with updated entity
        ResponseEntity<String> response = restTemplate.exchange(path + "/" + entity.getBody().getId(), HttpMethod.PUT,
                entity, String.class);

        // assert the response from the api
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // making a getByUserId to retrieve the user record
        ResponseEntity<UserDto> getResponse = restTemplate.getForEntity(path + "/" + td_Id, UserDto.class);

        // assert the response body from getByUserId request
        UserDto updatedUser = getResponse.getBody();
        assertEquals(td_Id, updatedUser.getId().toString());
        assertEquals(td_UserName, updatedUser.getUser_name());
        assertEquals(td_FirstName, updatedUser.getFirst_name());
        assertEquals(td_LastName, updatedUser.getLast_name());
        assertEquals(td_Email, updatedUser.getEmail());
        assertEquals(td_Role, updatedUser.getRole());
    }

    @Test
    public void user_tc0009_updateUserById_exception() throws JsonProcessingException {
        String td_UserId = "1001";
        String td_Error = "Bad Request";
//        String td_Message = "User not found in User Repository, please provide correct user id";
        String td_path = "/users/v1/" + td_UserId;
        // creating user entity for put
        UserDto entity = new UserDto(null, "", "", "", "", "");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> putEntity = new HttpEntity<>(entity, headers);

        // make a put call to edit the record using an api put request
        ResponseEntity<String> response = restTemplate.exchange(path + "/" + td_UserId, HttpMethod.PUT,
                putEntity, String.class);

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