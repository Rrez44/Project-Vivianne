package model.dto;

public class UpdateUser {
    private String id;
    private String passwordHash;
    private String salt;


    public UpdateUser(String id,String salt ,String passwordHash) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public String getSalt() {
        return salt;
    }
}

