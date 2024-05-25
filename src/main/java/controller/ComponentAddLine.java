package controller;

import ENUMS.ComfortRating;
import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.BusLine;
import repository.CompanyRepository;
import service.BusLineService;
import service.BusService;
import service.Translate;

import javax.swing.text.LabelView;
import java.time.LocalDateTime;

import static controller.RegisterLine.getBusLine;

public class ComponentAddLine {

    @FXML
    private Label txtCompanyNameAddLine;

    @FXML
    private Label txtBusModelAddLine;

    @FXML
    private Label txtPassangerSize;

    @FXML
    private Label txtPassangerSizeLabel;

    @FXML
    private Label txtCityFromLabelLine;

    @FXML
    private Label txtComfortRatingLabelLine;






    @FXML
    private Pane paneAddLine;



    public void handleCreateLine(ActionEvent event) {
        BusLine addBusLine =new BusLine(getBusLine().getLineId(),getBusLine().getStatus(),getBusLine().getStartTime(),getBusLine().getEndTime(), Session.getUser(),LocalDateTime.now(),getBusLine().getStops(),getBusLine().getStartLocation(),getBusLine().getEndLocation(), CompanyRepository.getCompanyFromName(txtCompanyNameAddLine.getText()), BusService.getBusByModelNumer(txtBusModelAddLine.getText()));
        if (!(addBusLine.getStops() == null)) {

            BusLineService.insertAddStop(getBusLine().getLineId(), addBusLine.getStops());
        }
        BusLineService.insertBusLine(addBusLine);
        Navigator.navigate(event,Navigator.HOME_PAGE);
    }



    public void getSpecificBusComany(String companyName, String busModel, int passangerSize, ComfortRating comfortRating) {
        Translate.translateForAllPanes(paneAddLine);
        txtCompanyNameAddLine.setText(companyName);
        txtBusModelAddLine.setText(busModel);
        txtPassangerSize.setText(String.valueOf(passangerSize));
        txtCityFromLabelLine.setText(ComfortRating.getTxtComfortRating(comfortRating));
    }

}
