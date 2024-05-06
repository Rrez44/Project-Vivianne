package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard extends BGmain {

    @FXML
    private MenuButton menuStatus;

    @FXML
    private TextField txtUsername;





    @FXML
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());

    }




    public void handleAddBus(ActionEvent event) {
    }

    public void handleSuspend(ActionEvent event) {

    }

}
