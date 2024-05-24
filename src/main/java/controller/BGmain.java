package controller;

import app.Navigator;
import app.Session;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.Translate;
import service.TranslateRecords;
import service.animations.ButtonMoveAnimation;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BGmain implements Initializable {

    @FXML
    protected MenuButton menuTranslate;

    @FXML
    protected Pane paneButtons;

    private static String lastClickedButtonId = null; // Static variable to persist across navigations
    private Button lastClickedButton = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (paneButtons != null) {
            Translate.translateForAllPanes(paneButtons);

            if (lastClickedButtonId != null) {
                for (Node node : paneButtons.getChildren()) {
                    if (node instanceof Button && node.getId().equals(lastClickedButtonId)) {
                        lastClickedButton = (Button) node;
                        lastClickedButton.setTranslateX(-10);
                        break;
                    }
                }
            }
        }
    }

    public void handleDashboard(ActionEvent event) {
        navigateWithButtonAnimation(event, (Button) event.getSource(), Navigator.HOME_PAGE);
    }

    public void handleStatistics(ActionEvent event) {
        navigateWithButtonAnimation(event, (Button) event.getSource(), Navigator.STATISTICS);
    }

    public void handleCreateLine(ActionEvent event) {
        navigateWithButtonAnimation(event, (Button) event.getSource(), Navigator.ADD_BUS_LINE);
    }

    public void handleReport(ActionEvent event) {
        navigateWithButtonAnimation(event, (Button) event.getSource(), Navigator.SEARCH_COMPANY_PAGE);
    }

    public void handleRegister(ActionEvent event) {
        navigateWithButtonAnimation(event, (Button) event.getSource(), Navigator.REGISTER_PAGE);
    }

    public void handleLogOut(ActionEvent event) {
        Navigator.firstNav = true;
        Navigator.loginNav = true;

        Session.setUser(null);
        Navigator.navigate(event, Navigator.LOGIN_PAGE);
    }

    public void handleBack(ActionEvent actionEvent) {
        if (Navigator.getSceneStack().isEmpty()) {
            showError(null, "Nowhere to go back to");
            return;
        }
        Navigator.goBack(actionEvent);
    }

    public void menuTranslate(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Translate.setLanguage(menuItem.getText());
        menuTranslate.setText(menuItem.getText());
        Translate.translateForAllPanes(paneButtons);
    }

    protected void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showConfirmation(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateWithButtonAnimation(ActionEvent event, Button button, String page) {
        ButtonMoveAnimation moveAnimation = new ButtonMoveAnimation(button, false);
        moveAnimation.play();

        if (lastClickedButton != null && lastClickedButton != button) {
            ButtonMoveAnimation resetAnimation = new ButtonMoveAnimation(lastClickedButton, true);
            resetAnimation.play();
        }

        lastClickedButton = button;
        lastClickedButtonId = button.getId();

        moveAnimation.setOnFinished(() -> {

                Navigator.navigate(event, page);

        });
    }


}
