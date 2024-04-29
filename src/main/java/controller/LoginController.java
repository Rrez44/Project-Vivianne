package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.dto.LoginUserDto;
import service.UserService;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

//    public void insertUser(){
//    }




    public void handleLogin(ActionEvent actionEvent) throws IOException {

        LoginUserDto loginUserDto = new LoginUserDto(txtUsername.getText(), txtPassword.getText());
        boolean checkLogin = UserService.login(loginUserDto);
//        UserService.insertSuperAdmin();

        if (checkLogin) {
            Navigator.navigate(actionEvent, Navigator.HOME_PAGE);
        }else{
            System.out.println("Login Failed");
        }




    }

}
