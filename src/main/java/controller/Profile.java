package controller;

import ENUMS.Role;
import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.dto.ChangePassword;
import service.ClearForm;
import service.Translate;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile extends BGmain implements Initializable {

    @FXML
    private Label txtFirstNameLastNameProfile;

    @FXML
    private Label txtUsernameProfile;

    @FXML
    private  Label txtEmailProfile;

    @FXML
    private Label txtRoleProfile;

    @FXML
    private Pane paneChangePassword;

    @FXML
    private PasswordField pwdCurrentPassword;

    @FXML
    private PasswordField pwdNewPassword;

    @FXML
    private PasswordField pwdConfirmNewPassword;

    @FXML
    private Pane paneProfile;

    @FXML
    private Button txtManageAdminsProfiel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(Session.getUser().getRole() != Role.SUPER_ADMIN){
            paneProfile.getChildren().remove(txtManageAdminsProfiel);
        }

        Translate.translateForAllPanes(paneProfile);
        txtFirstNameLastNameProfile.setText(Session.getUser().getFirstName() + " " + Session.getUser().getLastName());
        txtUsernameProfile.setText(Session.getUser().getUsername());
        txtEmailProfile.setText(Session.getUser().getEmail());
        txtRoleProfile.setText(Session.getUser().getRole().toString());



    }




    public void handleChangePassword(ActionEvent event) {
        paneChangePassword.setVisible(true);
    }

    public void handleManageAdmins(ActionEvent event) {
        Navigator.navigate(event,Navigator.GET_USERS);
    }

    public void handleCancelChangePassword(ActionEvent event) {
        paneChangePassword.setVisible(false);

    }

    public void handlechangePassword(ActionEvent event) {

        UserService.updateUser(new ChangePassword(Session.getUser().getId(),pwdCurrentPassword.getText(),pwdNewPassword.getText(),pwdConfirmNewPassword.getText()));

        ClearForm.clearFormInputs(paneChangePassword);
        paneChangePassword.setVisible(false);

    }
}
