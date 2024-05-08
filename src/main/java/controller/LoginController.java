package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.dto.LoginUserDto;
import repository.UserRepository;
import service.Session;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends BGmain {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;


    public void handleLogin(ActionEvent actionEvent) throws IOException {
        Login(actionEvent);
    }

    public void handlePressedEnter(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == KeyCode.ENTER ) {
            Login(keyEvent);
        }
    }


    public void Login(Event actionEvent) throws IOException {
        try {
            LoginUserDto loginUserDto = new LoginUserDto(txtUsername.getText(), txtPassword.getText());
            boolean checkLogin = UserService.login(loginUserDto);
            if (checkLogin) {
                Session session = Session.getInstance();
                String role =UserService.role(loginUserDto);
                session.setUsername(txtUsername.getText());
                session.setRole(role);
                Navigator.navigate(actionEvent,Navigator.HOME_PAGE);
            }else{
                showError("Login", "Login Failed Wrong Username or Password");
            }
        }
        catch (RuntimeException re){
            showError(null, re.getMessage());
        }

    }




}
