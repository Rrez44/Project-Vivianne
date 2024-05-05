package model;

import ENUMS.Role;

public class CreateUser {

    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String salt;
    private String passwordHash;
    private Role role;

    public CreateUser(String id, String first_name, String last_name, String username, String email, String salt, String passwordHash, Role role) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }
}
