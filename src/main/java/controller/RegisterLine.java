package controller;

import ENUMS.Status;
import ENUMS.TravelTime;
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
import model.Bus;
import model.BusLine;
//import otherFunctionality.AddHours;
import otherFunctionality.AddStop;
import otherFunctionality.AddTime;
import service.BusLineService;
import service.ClearForm;
import service.DateFormatter;
import service.Translate;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegisterLine extends BGmain implements Initializable {

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

    private HashMap<String, LocalDateTime> getStops = new HashMap<>();

    private LocalDateTime localDateTimeTo;
    private LocalDateTime localDateTimeFrom;



    public void addStops(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeTo = LocalDateTime.parse("2022-10-11 10:11:11" ,dateTimeFormatter);
        getStops.put(txtAddStop.getText(),localDateTimeTo);
        AddStop addStop = new AddStop();
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
        ClearForm.clearFormInputs(paneRegisterLine);
    }


    public void formatDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String travelTime = menuCityFrom.getText() +"_" +menuCityTo.getText();

        TravelTime travelTime1= TravelTime.valueOf(travelTime);
        String duration = travelTime1.getTime();

        StringBuilder from = new StringBuilder().append(dateDate.getValue() + " " +menuSelectHoursFrom.getText() +":"+menuSelectMinutesFrom.getText() +":"+"00");

        localDateTimeFrom = LocalDateTime.parse(from.toString(),dateTimeFormatter);
        String[] durationSplit = duration.split(":");
        String hours = durationSplit[0];
        String minutes = durationSplit[1];
        localDateTimeTo = localDateTimeFrom.plusHours(Integer.parseInt(hours)).plusMinutes(Integer.parseInt(minutes));

    }




    public void handleAddLine(ActionEvent event) {
        formatDateTime();
        menuSelectHoursTo.setText(String.valueOf(localDateTimeTo.getHour()));
        AddStop.restartForm();
        busLine = new BusLine(UUID.randomUUID().toString(), Status.ACTIVE,localDateTimeFrom,localDateTimeTo, Session.getUser(),LocalDateTime.now(),getStops ,menuCityFrom.getText(),menuCityTo.getText(),null,null);
        Navigator.navigate(event, Navigator.CREATE_LINE);
    }

    public static BusLine getBusLine() {
        return busLine;
    }

    public void handleSeelctCityFrom(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();
        menuCityFrom.setText(item.getText());

    }

    public void handleSelectCityTo(ActionEvent event) {

        MenuItem item = (MenuItem) event.getSource();
        menuCityTo.setText(item.getText());

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

    public void changeTime(){
        formatDateTime();
        menuSelectHoursTo.setText(String.valueOf(localDateTimeTo.getHour()));
        menuSelectMinutesTo.setText(String.valueOf(localDateTimeTo.getMinute()));
    }
}
