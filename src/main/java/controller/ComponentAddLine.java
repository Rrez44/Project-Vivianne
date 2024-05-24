package controller;

import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.BusLine;
import repository.CompanyRepository;
import service.BusLineService;
import service.BusService;

import java.time.LocalDateTime;

import static controller.RegisterLine.getBusLine;

public class ComponentAddLine {

    @FXML
    private Label txtCompanyName;

    @FXML
    private Label txtBusModel;

    @FXML
    private Label txtPassangerSize;



    public void handleCreateLine(ActionEvent event) {

        BusLine addBusLine =new BusLine(getBusLine().getLineId(),getBusLine().getStatus(),getBusLine().getStartTime(),getBusLine().getEndTime(), Session.getUser(),LocalDateTime.now(),getBusLine().getStops(),getBusLine().getStartLocation(),getBusLine().getEndLocation(), CompanyRepository.getCompanyFromName(txtCompanyName.getText()), BusService.getBusByModelNumer(txtBusModel.getText()));
        BusLineService.insertAddStop(addBusLine.getCompanyAssigned().getCompanyId(), BusService.getBusByModelNumer(txtBusModel.getText()).getBusId(),addBusLine.getStops());
        BusLineService.insertBusLine(addBusLine);
        Navigator.navigate(event,Navigator.HOME_PAGE);

    }



    public void getSpecificBusComany(String companyName, String busModel, int passangerSize) {
        txtCompanyName.setText(companyName);
        txtBusModel.setText(busModel);
        txtPassangerSize.setText(String.valueOf(passangerSize));
    }

}
