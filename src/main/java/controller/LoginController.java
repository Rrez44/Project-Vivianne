package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    Navigator.class.getResource(Navigator.HOME_PAGE)
            );
            try{
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }else{
            System.out.println("Login Failed");
        }

    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtEmri.setText("Emri");
//    }
}
