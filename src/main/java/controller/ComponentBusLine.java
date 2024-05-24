package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.BusLine;

import java.time.LocalDateTime;

public class ComponentBusLine {

    @FXML
    private Label txtGetStartLocation;

    @FXML
    private Label txtGetEndLocation;

    @FXML
    private Label txtStatus;

    @FXML
    private Label txtStart;


    private  BusLine passedBusLine;

    public void setData(String from, String to, String status,LocalDateTime timeFrom, LocalDateTime timeTo){
        txtGetStartLocation.setText(from);
        txtGetEndLocation.setText(to);
        txtStatus.setText(status);
        txtStart.setText(timeFrom.toString().replace("T", "  ") + " -> " +timeTo.getHour() +":" + timeTo.getMinute());
        setActivityColor();
    }
    private void setActivityColor(){
        switch (txtStatus.getText()){
            case "ACTIVE": txtStatus.setStyle("-fx-text-fill: #00b61f;"); break;
            case "COMPLETED": txtStatus.setStyle("-fx-text-fill: #ffa51b;"); break;
            default : txtStatus.setStyle("-fx-text-fill: #ff0000;");
        }
    }
    public void passBusLine(BusLine bl){
        passedBusLine = bl;
    }


    public void handleManage(ActionEvent actionEvent) {

        ManageBusLine.passBusLine(passedBusLine);
        Navigator.navigate(actionEvent, Navigator.MANAGE_LINE);
    }
}
