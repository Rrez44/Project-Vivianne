package controller;

import ENUMS.ComfortRating;
import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Bus;
import service.BusService;

public class ComponentBusSearch {
    @FXML
    private Label txtBusModel;
    @FXML
    private Label txtVin;
    @FXML
    private Label txtComfortRating;
    @FXML
    private Label txtActivity;
    public void handleManage(ActionEvent actionEvent) {
        ManageBus.passBus(BusService.getBusByVin(txtVin.getText()));
        Navigator.navigate(actionEvent, Navigator.MANAGE_BUS);
    }


    public void setData(Bus bus) {
        txtBusModel.setText(bus.getBusModel());
        txtVin.setText(bus.getVin());
        txtComfortRating.setText(ComfortRating.getTxtComfortRating(bus.getComfortRating()));
        txtActivity.setText(bus.getActivityStatus().name());
        if(txtActivity.getText().equals("ACTIVE")){
            txtActivity.setStyle("-fx-text-fill:  #1DB954;");
        }else{
            txtActivity.setStyle("-fx-text-fill: #ef233c;");
        }
    }
}
