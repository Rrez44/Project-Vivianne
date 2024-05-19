package controller;

import ENUMS.Role;
import INTERFACES.Identifiable;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.dto.UserDto;
import service.ClearForm;
import service.GenerateEmail;
import service.UserService;

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

        if(Session.getUser().getRole().equals("SUPER_ADMIN")){
        }else{
            menuSelectPriority.getItems().remove(0);
            menuSelectPriority.getItems().remove(0);
        }

    }



    public void handleSignUp(ActionEvent event) {
        email = GenerateEmail.email(txtFirstName.getText(), txtLastName.getText(), UserService.checkDuplicate(txtFirstName.getText(),txtLastName.getText()));
        String role = menuSelectPriority.getText();
//        System.out.println(Session.getInstance().getRole());
        UserDto userDto = new UserDto(generateId(),txtFirstName.getText(),txtLastName.getText(),email,txtUsername.getText(),pwdPassword.getText(),pwdConfirmPassword.getText(), Role.valueOf(role));
        if(UserService.signUP(userDto)){
            showConfirmation("Register","Register Successful");
            ClearForm.clearFormInputs(paneRegister);

        }else{
            showError("Register","Error");
        }

    }

    public void createEmail() {
        String email = GenerateEmail.email(txtFirstName.getText(), txtLastName.getText(), UserService.checkDuplicate(txtFirstName.getText(),txtLastName.getText()));
        txtEmail.setText(email);
    }

    public void handleSuspend(ActionEvent event) {
        ClearForm.clearFormInputs(paneRegister);
    }




}
