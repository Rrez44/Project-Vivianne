package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Dashboard extends BGmain {

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

}
