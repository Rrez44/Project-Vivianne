package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Bus;
import repository.BusRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchBus extends BGmain implements Initializable {
    private static model.Company company;

    @FXML
    private VBox paneQueryResult;


    public static void passCompany(model.Company cmp){
        company = cmp;
    }
    public void handleVinSearch(ActionEvent actionEvent) {
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleAddBus(ActionEvent actionEvent) {
        Navigator.navigate(actionEvent, Navigator.ADD_BUS_PAGE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBuses(BusRepository.loadInitial(company));
    }

    private void displayBuses(List<Bus> busList){
            double totalHeight=0;
            paneQueryResult.getChildren().clear();
            for (model.Bus bus : busList) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/searchBusComponent.fxml"));
                    Pane busPane = loader.load();
                    ComponentBusSearch busController = loader.getController();
                    busController.setData(bus);
                    paneQueryResult.getChildren().add(busPane);

                    totalHeight += busPane.getPrefHeight() + paneQueryResult.getSpacing();

                } catch (IOException e) {
                    showError("Error displaying companies", e.getMessage());
                }
                paneQueryResult.setPrefHeight(totalHeight);
            }
    }
}
