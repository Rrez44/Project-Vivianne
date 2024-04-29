package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginController {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    public void handleLogin(ActionEvent actionEvent) throws IOException {

        Navigator.navigate(actionEvent, Navigator.HOME_PAGE);
    }

}
