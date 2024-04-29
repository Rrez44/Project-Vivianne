package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class dashboard {
    @FXML
    private MenuButton menuStatus;

    public void handleDashboard(){

    }

    public void handleCreateLine(){

    }

    public void handleReport(){

    }

    public void handleRegister(){

    }

    public void handleLogOut(){

    }
    @FXML
    private void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());
    }

}
