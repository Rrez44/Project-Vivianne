package controller;

import ENUMS.ActivityStatus;
import ENUMS.ComfortRating;
import INTERFACES.StarManager;
import app.Navigator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import model.Bus;
import model.Company;
import repository.BusRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageBus extends BGmain implements StarManager, Initializable {
    @FXML
    private SVGPath svgStar1;
    @FXML
    private SVGPath svgStar2;
    @FXML
    private SVGPath svgStar3;
    @FXML
    private SVGPath svgStar4;
    @FXML
    private SVGPath svgStar5;
    @FXML
    private MenuButton mbtnBusType;
    @FXML
    private Label txtCurrentStatus;
    @FXML
    private Button btnManageActivity;

    @FXML
    private TextField txtVin;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtPassengerCapacity;
    private static Bus passedBus;




    private int cRating = 0;
    public void changeColors(int num){
        SVGPath[] svgStars={svgStar1,svgStar2,svgStar3,svgStar4,svgStar5};

        for(int i=0;i< svgStars.length;i++){

            if(i<num){
                svgStars[i].setFill(Color.GOLD);
                System.out.println(i);
                cRating = num;
            }else{
                svgStars[i].setFill(Color.WHITE);
            }

        }
    }

    public void handleFillStar1(ActionEvent event) {
        changeColors(1);
    }
    public void handleFillStar2(ActionEvent event) {
        changeColors(2);
    }
    public void handleFillStar3(ActionEvent event) {
        changeColors(3);
    }
    public void handleFillStar4(ActionEvent event) {
        changeColors(4);
    }
    public void handleFillStar5(ActionEvent event) {
        changeColors(5);
    }

    public void handleUpdate(ActionEvent actionEvent) {
        if (passedBus.getComfortRating().getRating() == cRating && passedBus.getActivityStatus().name().equals(txtCurrentStatus.getText())){
            showError("Nothing has changed", "not wasting an update if youre not gonna change anything!");
            return;
        }
        passedBus.setActivityStatus(ActivityStatus.valueOf(txtCurrentStatus.getText()));
        passedBus.setComfortRating(ComfortRating.fromInt(cRating));
        try {
            BusRepository.updateBus(passedBus);
            showConfirmation("Updated bus", "Successful operation");
            Navigator.navigate(actionEvent, Navigator.COMPANY_PAGE);
        }catch (RuntimeException re){
            showError("Could not update bus", re.getMessage());
        }
    }

    public void handleDiscard(ActionEvent actionEvent) {
        Navigator.goBack(actionEvent);
    }

    public void manageActivity(ActionEvent actionEvent) {
        if (txtCurrentStatus.getText().equals("ACTIVE")){
            txtCurrentStatus.setText("SUSPENDED");
        }
        else {
            txtCurrentStatus.setText("ACTIVE");
        }
        updateActivity();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtVin.setText(passedBus.getVin());
        txtModel.setText(passedBus.getBusModel());
        mbtnBusType.setText(passedBus.getBusType().name());
        System.out.println(svgStar1 == null);
        txtCurrentStatus.setText(passedBus.getActivityStatus().name());
        txtPassengerCapacity.setText(String.valueOf(passedBus.getPassangerCapacity()));
        updateActivity();

        Platform.runLater(() -> {
            changeColors(passedBus.getComfortRating().getRating());
        });

    }
    private void updateActivity(){
        if(txtCurrentStatus.getText().equals("ACTIVE")){
            txtCurrentStatus.setStyle("-fx-text-fill:  #1DB954;");
            btnManageActivity.setText("SUSPEND");
        }else{
            txtCurrentStatus.setStyle("-fx-text-fill: #ef233c;");
            btnManageActivity.setText("ACTIVATE");
        }
    }
    public static void passBus(Bus bus){
        passedBus = bus;
    }
}
