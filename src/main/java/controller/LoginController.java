package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    public void handleLogin(ActionEvent actionEvent) throws IOException {

        Navigator.navigate(actionEvent, Navigator.HOME_PAGE);
    }

}
