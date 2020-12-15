package com.sdet.auto.users.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDto {

    private Long id;

    @NotEmpty(message="user_name is a required field.  Please provide a user_name")
    @Size(min=2, message="user_name should contain at least 2 characters")
    private String user_name;

    @NotEmpty(message="first_name is a required field.  Please provide a first_name")
    @Size(min=2, message="first_name should contain at least 2 characters")
    private String first_name;

    @NotEmpty(message="last_name is a required field.  Please provide a last_name")
    @Size(min=2, message="last_name should contain at least 2 characters")
    private String last_name;

    @NotEmpty(message="email is a required field.  Please provide a email")
    @Size(min=2, message="email should contain at least 2 characters")
    private String email;

    @NotEmpty(message="role is a required field.  Please provide a role")
    @Size(min=2, message="role should contain at least 2 characters")
    private String role;

    public UserDto() {
    }

    public UserDto(Long id, String user_name, String first_name, String last_name, String email, String role) {
        this.id = id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}