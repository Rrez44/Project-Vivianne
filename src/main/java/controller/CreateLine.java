package controller;

import app.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.Bus;
import model.BusLine;
import model.Company;
import model.User;
import service.BusLineService;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

//import static controller.RegisterLine.busLine;
import static controller.RegisterLine.getBusLine;

public class CreateLine implements Initializable  {

    @FXML
    private AnchorPane paneGetAllAvailableBus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            getAllAvailableCompanies(BusLineService.getCompany(getBusLine().getStartLocation()));

    }


    public void getAllAvailableCompanies(List<BusLine> busLines){
        double totalHeight=0;
        paneGetAllAvailableBus.getChildren().clear();
        for (BusLine bus :busLines) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/addLineComponent.fxml"));
                AnchorPane busPane = loader.load();
                ComponentAddLine addLineController = loader.getController();
                addLineController.getSpecificBusComany(bus.getCompanyAssigned().getCompanyName(),bus.getBusModel().getBusModel(),bus.getPassengerCapacity());
                busPane.setLayoutY(totalHeight);
                paneGetAllAvailableBus.getChildren().add(busPane);
                totalHeight += busPane.getPrefHeight();

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        paneGetAllAvailableBus.setPrefHeight(totalHeight);
        }

   }



