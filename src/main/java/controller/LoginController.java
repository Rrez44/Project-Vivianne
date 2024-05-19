package controller;

import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;
import model.dto.LoginUserDto;
import service.UserService;

import java.io.IOException;
import java.util.SortedMap;

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
                System.out.println("DBUSER:"+UserService.getUser(txtUsername.getText()).getId());
                Session.setUser(UserService.getUser(txtUsername.getText()));
                Session.setUser(UserService.getUser(txtUsername.getText()));
                System.out.println(Session.getUser().getId());
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
