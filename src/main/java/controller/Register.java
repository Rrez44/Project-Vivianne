package controller;

import ENUMS.Role;
import INTERFACES.Identifiable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.dto.UserDto;
import service.ClearForm;
import service.GenerateEmail;
import service.Session;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class Register extends BGmain implements Identifiable{

    @FXML
    private Pane paneRegister;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dateBirthday;
    
    @FXML
    private PasswordField pwdPassword;
    
    @FXML
    private PasswordField pwdConfirmPassword;

    @FXML
    private MenuButton menuSelectPriority;

    @FXML
    private String  email;



    public void handleSelectPriority(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSelectPriority.setText(item.getText());


    }
    @FXML
    public void initialize() {
        txtFirstName.textProperty().addListener((observable, oldValue, newValue) -> createEmail());
        txtLastName.textProperty().addListener((observable, oldValue, newValue) -> createEmail());
    }



    public void handleSignUp(ActionEvent event) {
        email =GenerateEmail.email(txtFirstName.getText(),txtLastName.getText());
        String role = menuSelectPriority.getText();
        UserDto userDto = new UserDto(generateId(),txtFirstName.getText(),txtLastName.getText(),email,txtUsername.getText(),pwdPassword.getText(),pwdConfirmPassword.getText(), Role.valueOf(role));
        if(UserService.signUP(userDto)){
            showConfirmation("Register","Register Successful");
            ClearForm.clearFormInputs(paneRegister);

        }else{
            showError("Register","Error");
        }

    }

    public void createEmail() {
        String email = GenerateEmail.email(txtFirstName.getText(),txtLastName.getText());
        txtEmail.setText(email);
    }

    public void handleSuspend(ActionEvent event) {
    }


}
