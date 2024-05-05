package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

//import java.net.URL;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard extends BGmain implements Initializable {

    @FXML
    private MenuButton menuStatus;

    @FXML
    private TextField txtUsername;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        txtUsername.setText("hello");

    }

    @FXML
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());

//        txtEmri.setText(Session.getInstance().getUserName());
    }




    public void handleAddBus(ActionEvent event) {
    }

    public void handleSuspend(ActionEvent event) {

    }

    public void handleSetUsername(ActionEvent event) {
    }

//    public void handleSetUsername(ActionEvent event) {
//
//    }
}
