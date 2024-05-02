package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class NewRegisterLine {

    @FXML
    private TextField txtAddStop;

    @FXML
    private Label txtStopName;

    @FXML
    private AnchorPane paneAddStop;

    @FXML
    private StackPane paneStackAddStops;

    private static int i=0;
    private  int x = 154;
    private static int y = 61;



    public void addStops(){
        if(!txtAddStop.getText().isEmpty()){

//            TextField tf = new TextField(txtAddStop.getText());
//            Label llb = new Label(txtStopName.getText() +i);
//            i++;
//
//            tf.setId(tf.getText() + i);
//            tf.setLayoutX(x);
//
//            y = y+35;
//            tf.setLayoutY(y);
//            llb.setLayoutY(y);
//            paneAddStop.setPrefHeight(paneAddStop.getHeight()+20);
//            paneAddStop.getChildren().addAll(tf,llb);

        }

    }


    public void handleSuspend(ActionEvent event) {
        addStops();
    }

    public void handleAddBus(ActionEvent event) {
    }
}
