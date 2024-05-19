package controller;

import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Bus;
import model.BusLine;
import service.BusLineService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());

    }




    public void handleAddBus(ActionEvent event) {
    }

    public void handleSuspend(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
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

                System.out.println(bus.getStartLocation());
                addLineController.setData(bus.getStartLocation(), bus.getEndLocation(), bus.getStatus().toString(), bus.getStartTime());

                // Set layoutY for the current busPane
                busPane.setLayoutY(totalHeight);

                // Add the busPane to paneAddCompanyLine
                paneAddCompanyLine.getChildren().add(busPane);

                // Update totalHeight by adding the height of the current busPane plus a gap of 50 units
                totalHeight += busPane.getPrefHeight() + 5;
            }

            // After all panes are added, adjust the preferred height of paneAddCompanyLine

            // Encapsulate paneAddCompanyLine within a ScrollPane
            ScrollPane scrollPane = new ScrollPane(paneAddCompanyLine);
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to adjust its width

            // Set the content of your parent container to the ScrollPane
            // Assuming your parent container is a VBox named parentContainer
//            parentContainer.getChildren().setAll(scrollPane);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception for debugging
        }
        paneAddCompanyLine.setPrefHeight(totalHeight);

    }
}
