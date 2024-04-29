package model;

import ENUMS.Role;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String salt;
    private String hashedPassword;
    private Role role;


    public User(int id, String firstName, String lastName, String username, String email, String salt, String hashedPassword, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() {
        return salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Role getRole() {
        return role;
    }
}
