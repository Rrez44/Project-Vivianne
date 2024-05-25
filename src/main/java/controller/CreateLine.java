package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.BusLine;
import service.BusLineService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateLine extends BGmain implements Initializable  {

    @FXML
    private AnchorPane paneGetAllAvailableBus;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           BusLine busline = RegisterLine.getBusLine();

            getAllAvailableCompanies(BusLineService.getCompany(busline.getStartLocation(), busline.getEndLocation(), busline.getStartTime(), busline.getEndTime()));

    }


    public void getAllAvailableCompanies(List<BusLine> busLines){
        double totalHeight=0;
        paneGetAllAvailableBus.getChildren().clear();
        if (busLines.isEmpty()){showError("No eligible buses", null); Navigator.navigate(new ActionEvent(), Navigator.CREATE_LINE);}

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



