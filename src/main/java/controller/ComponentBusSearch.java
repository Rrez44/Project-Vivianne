package controller;

import ENUMS.ComfortRating;
import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Bus;
import service.BusService;
import service.Translate;

public class ComponentBusSearch {
    @FXML
    private Label txtBusModel;
    @FXML
    private Label txtVinSearchBus;
    @FXML
    private Label txtComfortRating;
    @FXML
    private Label txtActivity;

    @FXML
    private Pane paneBusSearch;

    @FXML
    private Button btnManageSearchBus;

    public void handleManage(ActionEvent actionEvent) {
        ManageBus.passBus(BusService.getBusByVin(txtVinSearchBus.getText()));
        Navigator.navigate(actionEvent, Navigator.MANAGE_BUS);
    }


    public void setData(Bus bus) {
        Translate.translateForAllPanes(paneBusSearch);
        txtBusModel.setText(bus.getBusModel());
        txtVinSearchBus.setText(bus.getVin());
        txtComfortRating.setText(ComfortRating.getTxtComfortRating(bus.getComfortRating()));
        txtActivity.setText(bus.getActivityStatus().name());
        if(txtActivity.getText().equals("ACTIVE")){
            txtActivity.setStyle("-fx-text-fill:  #1DB954;");
        }else{
            txtActivity.setStyle("-fx-text-fill: #ef233c;");
        }
    }
}
