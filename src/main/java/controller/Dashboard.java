package controller;

import app.Navigator;
import app.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Bus;
import model.BusLine;
import service.BusLineService;
import service.Translate;
import service.TranslateRecords;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class Dashboard extends BGmain implements Initializable {

    @FXML
    private MenuButton menuStatus;

    @FXML
    private Label txtName;

    @FXML
    private AnchorPane paneAddCompanyLine;

    @FXML
    private TextField txtFrom;

    @FXML
    protected Pane paneSearchLines;

    @FXML
    protected Button btnGoToProfile;






    @FXML
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());

//        TranslateRecords.translateFormInputs(Translate.getInstance().getCurrentLanguage(),paneButtons);

    }




    public void handleAddBus(ActionEvent event) {
    }

    public void handleSuspend(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            super.initialize(url, resourceBundle);
            Translate.translateForAllPanes(paneSearchLines);
             txtName.setText(Session.getUser().getUsername());


         }catch (NullPointerException e){


        }
    }
    public void handleSearch(ActionEvent event) {
        double totalHeight = 0;
        System.out.println("hi");
        System.out.println(txtFrom.getText());
        List<BusLine> busLine = BusLineService.getBusLine(txtFrom.getText());

        System.out.println(busLine.size());
        paneAddCompanyLine.getChildren().clear();
        try {
            for (BusLine bus : busLine) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/busLineComponent.fxml"));
                AnchorPane busPane = loader.load();
                ComponentBusLine addLineController = loader.getController();

                addLineController.setData(bus.getStartLocation(), bus.getEndLocation(), bus.getStatus().toString(), bus.getStartTime(), bus.getEndTime());
                addLineController.passBusLine(bus);
                busPane.setLayoutY(totalHeight);

                paneAddCompanyLine.getChildren().add(busPane);

                totalHeight += busPane.getPrefHeight() + 5;
            }


            ScrollPane scrollPane = new ScrollPane(paneAddCompanyLine);
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to adjust its width

        } catch (Exception e) {
            e.printStackTrace();
        }
        paneAddCompanyLine.setPrefHeight(totalHeight);

    }


    public void handleGotToProfile(ActionEvent event) {
        Navigator.navigate(event,Navigator.PROFILE_PAGE);
    }
}
