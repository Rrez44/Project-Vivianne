package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Dashboard extends BGmain{

    @FXML
    private MenuButton menuStatus;

    @FXML
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());
    }

}
