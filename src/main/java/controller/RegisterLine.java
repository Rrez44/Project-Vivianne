package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import otherFunctionality.AddStop;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterLine extends BGmain implements Initializable {

    @FXML
    private TextField txtAddStop;

    @FXML
    private Label txtStopName;

    @FXML
    private AnchorPane paneAddStop;

    @FXML
    private StackPane paneStackAddStops;


    public void addStops(){
        AddStop addStop = new AddStop();
        addStop.addStop(txtAddStop.getText(),txtStopName.getText(),paneAddStop);
        txtAddStop.clear();
        paneAddStop.setPrefHeight(paneAddStop.getHeight()+30);
    }


    public void handleSuspend(ActionEvent event) {
        addStops();
    }

    public void handleAddBus(ActionEvent event) {
    }


    public void addStopOnEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER ){
            addStops();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
