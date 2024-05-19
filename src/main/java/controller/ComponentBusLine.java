package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    public void setData(String from, String to, String status,LocalDateTime localDateTime){
        txtGetStartLocation.setText(from);
        txtGetEndLocation.setText(to);
        txtStatus.setText(status);
        txtStart.setText(localDateTime.toString());

    }




}
