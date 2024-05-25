package model.dto;


import ENUMS.Role;

import java.time.LocalDateTime;

public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
    private LocalDateTime birthday;


    public UserDto(String id, String firstName, String lastName, String email, String username, String password, String confirmPassword, Role role,LocalDateTime birthday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Role getRole() {
        return role;
    }
    public LocalDateTime getBirthday() {
        return birthday;
    }
}