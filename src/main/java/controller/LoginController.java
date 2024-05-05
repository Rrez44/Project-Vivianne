package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.dto.LoginUserDto;
import repository.UserRepository;
import service.Session;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BGmain {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;


    @FXML
    private Label txtEmri;


    public void handleLogin(ActionEvent actionEvent) throws IOException {

        LoginUserDto loginUserDto = new LoginUserDto(txtUsername.getText(), txtPassword.getText());
        boolean checkLogin = UserService.login(loginUserDto);

        if (checkLogin) {
            Session session = Session.getInstance();
            String role =UserService.role(loginUserDto);
            session.setUsername(txtUsername.getText());
            session.setRole(role);

            Navigator.navigate(actionEvent, Navigator.HOME_PAGE);
        }else{
            System.out.println("Login Failed");
        }

    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtEmri.setText("Emri");
//    }
}
