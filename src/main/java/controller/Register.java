package controller;

import ENUMS.Role;
import INTERFACES.Identifiable;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.dto.UserDto;
import service.ClearForm;
import service.GenerateEmail;
import service.Translate;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class Register extends BGmain implements Identifiable, Initializable {

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

    @FXML
    private Label txtLabelPane;

    @FXML
    private StackPane stackPaneRegisterLine;





    public void handleSelectPriority(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSelectPriority.setText(item.getText());


    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Translate.translateForAllPanes(paneRegister);
        txtFirstName.textProperty().addListener((observable, oldValue, newValue) -> createEmail());
        txtLastName.textProperty().addListener((observable, oldValue, newValue) -> createEmail());

        if(Session.getUser().getRole() == Role.SUPER_ADMIN){
        }else{
            menuSelectPriority.getItems().remove(0);
            menuSelectPriority.getItems().remove(0);
        }

    }



    public void handleSignUp(ActionEvent event) {
        email = GenerateEmail.email(txtFirstName.getText(), txtLastName.getText(), UserService.checkDuplicate(txtFirstName.getText(),txtLastName.getText()));
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
        String email = GenerateEmail.email(txtFirstName.getText(), txtLastName.getText(), UserService.checkDuplicate(txtFirstName.getText(),txtLastName.getText()));
        txtEmail.setText(email);
    }

    public void handleSuspend(ActionEvent event) {
        ClearForm.clearFormInputs(paneRegister);
    }




}
