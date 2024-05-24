package model;

import ENUMS.Role;
import INTERFACES.Identifiable;

public class User implements Identifiable {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String salt;
    private String hashedPassword;
    private Role role;


    public User(String firstName, String lastName, String username, String email, String salt, String hashedPassword, Role role) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

        public User(String id, String firstName, String lastName, String username, String email, String salt, String hashedPassword, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = role;
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

