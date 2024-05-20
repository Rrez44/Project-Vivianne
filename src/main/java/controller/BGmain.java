package controller;

import app.Navigator;
import app.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.xpath.XPathNamespace;
import service.Translate;
import service.TranslateRecords;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract   class BGmain  implements Initializable {


    @FXML
    protected MenuButton menuTranslate;

    @FXML
    protected Pane paneButtons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         Translate.translateForAllPanes(paneButtons);

    }


    public void handleDashboard(ActionEvent event){
        Navigator.navigate(event,Navigator.HOME_PAGE);
    }

    public void handleStatistics(ActionEvent event){
        Navigator.navigate(event, Navigator.STATISTICS);
    }

    public void handleCreateLine(ActionEvent event){
        Navigator.navigate(event,Navigator.ADD_BUS_LINE);
    }

    public void handleReport(ActionEvent event){
        Navigator.navigate(event,Navigator.SEARCH_COMPANY_PAGE);

    }

    public void handleRegister(ActionEvent event){
        Navigator.navigate(event,Navigator.REGISTER_PAGE);
    }

    public void handleLogOut(ActionEvent event)
    {
        Navigator.firstNav = true;
        Navigator.loginNav = true;

        Session.setUser(null);
        Navigator.navigate(event,Navigator.LOGIN_PAGE);
    }
    protected void showError(String header, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showConfirmation(String header, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void handleBack(ActionEvent actionEvent) {
        if (Navigator.getSceneStack().isEmpty()){
            showError(null, "Nowhere to go back to");
            return;
        }
        Navigator.goBack(actionEvent);
    }

    public void menuTranslate(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Translate.setLanguage(menuItem.getText());
        Translate.setLanguage("English");
        menuTranslate.setText(menuItem.getText());
        Translate.setLanguage(menuTranslate.getText());
        translate(Translate.getLanguage(),paneButtons);
        ;
    }




    public  void translate(String setLanguage,Pane pane){

        Translate.setLanguage(setLanguage);
        TranslateRecords.translateFormInputs(Translate.getLanguage(),pane);

    }

}
