package controller;

import ENUMS.Status;
import ENUMS.TravelTime;
import INTERFACES.Identifiable;
import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.BusLine;
import otherFunctionality.AddStop;
import otherFunctionality.AddTime;
import service.DateConversion;
import service.Translate;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RegisterLine extends BGmain implements Initializable, Identifiable {

    @FXML
    private MenuButton menuCityFrom;

    @FXML
    private MenuButton menuCityTo;

    @FXML
    private MenuButton menuSelectHoursFrom;

    @FXML
    private MenuButton menuSelectMinutesFrom;


    @FXML
    private MenuButton menuSelectHoursTo;

    @FXML
    private MenuButton menuSelectMinutesTo;
    @FXML
    private DatePicker dateDate;

    @FXML
    private TextField txtStartTime;

    @FXML
    private TextField txtArrivalTime;

    @FXML
    private TextField txtAddStop;

    @FXML
    private Label txtStopName;

    @FXML
    private AnchorPane paneAddStop;

    @FXML
    private StackPane paneStackAddStops;

    @FXML
    private Pane paneRegisterLine;

    @FXML
    private StackPane stackPaneRegisterLine;

    @FXML
    private StackPane stackPaneAddStops;

    private static BusLine busLine;


    private LocalDateTime localDateTimeTo;
    private LocalDateTime localDateTimeFrom;
    public  String travelTime ;


    private AddStop addStop = new AddStop();

    public void addStops(){
        addStop.addStop(txtAddStop.getText(),txtStopName.getText(),paneAddStop);
        txtAddStop.clear();
        paneAddStop.setPrefHeight(paneAddStop.getHeight()+30);


    }



    public void addStopOnEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER ){
            addStops();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        Translate.translateForAllPanes(paneRegisterLine);
        Translate.translateForAllPanes(stackPaneAddStops);
        menuSelectHoursFrom.textProperty().addListener((observable, oldValue, newValue) -> changeTime());
        menuSelectMinutesFrom.textProperty().addListener((observable, oldValue, newValue) -> changeTime());

        AddTime.addHour(menuSelectHoursFrom,this::handleSelectHourFrom);
        AddTime.addMinutes(menuSelectMinutesFrom,this::handleSelectMinutesFrom);
        AddTime.addHour(menuSelectHoursTo,this::handleSelectHoursTo);
        AddTime.addMinutes(menuSelectMinutesTo,this::handleSelectMinutesTo);
    }

    public void handleClear(ActionEvent event) {
        Navigator.navigate(event, Navigator.CREATE_LINE);
    }

    public void handleAddLine(ActionEvent event) {

        if( DateConversion.localDate(dateDate.getValue(),"00:00:00").isBefore(LocalDateTime.now().plusDays(-1))){
            showError("Invalid Date","Cannot add line in Past ");
            return;
        }



            travelTime = menuCityFrom.getText() +"_" +menuCityTo.getText();

        localDateTimeFrom =DateConversion.fromDateTimeComponents(dateDate.getValue(),menuSelectHoursFrom.getText(),menuSelectMinutesFrom.getText());
        localDateTimeTo =DateConversion.calculateEndDateTime(localDateTimeFrom,TravelTime.valueOf(travelTime).getTime());

        AddStop.restartForm();

        busLine = new BusLine(generateId(), Status.ACTIVE,localDateTimeFrom,localDateTimeTo, Session.getUser(),LocalDateTime.now() ,menuCityFrom.getText(),menuCityTo.getText(),null,null);
        busLine.setStops(addStop.getStops());
        Navigator.navigate(event, Navigator.CREATE_LINE);
    }

    public static BusLine getBusLine() {
        return busLine;
    }

    public void handleSeelctCityFrom(ActionEvent event) {
        try {
            MenuItem item = (MenuItem) event.getSource();
            menuCityFrom.setText(item.getText());
            menuCityFrom.setDisable(true);
        }catch (Exception e){
        }
    }

    public void handleSelectCityTo(ActionEvent event) {
        try {
            MenuItem item = (MenuItem) event.getSource();
            menuCityTo.setText(item.getText());
            menuCityTo.setDisable(true);
        }catch (Exception e){

        }
    }

    public void handleSelectHourFrom(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSelectHoursFrom.setText(item.getText());
    }

    public void handleSelectMinutesFrom(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();

        menuSelectMinutesFrom.setText(item.getText());
    }

    public void handleSelectMinutesTo(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSelectMinutesTo.setText(item.getText());
    }

    public void handleSelectHoursTo(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        menuSelectHoursTo.setText(item.getText());
    }

    public void changeTime() {
        try {
        if(menuSelectHoursFrom.getText().equals("Hours") || menuSelectMinutesFrom.getText().equals("Minutes") ) {
            return;
        }
            travelTime = menuCityFrom.getText() + "_" + menuCityTo.getText();
            localDateTimeFrom = DateConversion.fromDateTimeComponents(dateDate.getValue(), menuSelectHoursFrom.getText(), menuSelectMinutesFrom.getText());
            localDateTimeTo = DateConversion.calculateEndDateTime(localDateTimeFrom, TravelTime.valueOf(travelTime).getTime());
            menuSelectHoursTo.setText(String.valueOf(localDateTimeTo.getHour()));
            menuSelectMinutesTo.setText(String.valueOf(localDateTimeTo.getMinute()));
        }catch (Exception e){


        }
    }
}
