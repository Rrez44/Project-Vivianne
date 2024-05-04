package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Register extends BGmain{


    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private DatePicker dateBirthday;
    
    @FXML
    private PasswordField pwdPassword;
    
    @FXML
    private PasswordField pwdConfirmPassword;


    public void handleAddBus(ActionEvent event) {
    }

    public void handleSuspend(ActionEvent event) {
    }
}
