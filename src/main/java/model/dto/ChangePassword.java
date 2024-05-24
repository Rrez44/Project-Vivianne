package model.dto;


public class ChangePassword {
    private String id;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public ChangePassword(String  id, String currentPassword, String newPassword, String confirmNewPassword) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getId() {
        return id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
}