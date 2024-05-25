package controller;

import ENUMS.ComfortRating;
import ENUMS.Role;
import ENUMS.Status;
import app.Navigator;
import app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.BusLine;
import service.BusLineService;
import service.Translate;
import service.animations.BusAnimation;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ManageBusLine extends BGmain implements Initializable {

    @FXML
    private Label txtPlaceFrom;
    @FXML
    private Label txtPlaceTo;
    @FXML
    private Label txtTimeFrom;
    @FXML
    private Label txtTimeTill;
    @FXML
    private Label txtDate;
    @FXML
    private Label txtCompanyAssigned;
    @FXML
    private Label txtBusModel;
    @FXML
    private Label txtComfortRating;
    @FXML
    private Label txtPassangerCapacity;
    @FXML
    private Label txtTotalStops;
    @FXML
    private Label txtBusLineId;
    @FXML
    private Label txtActivityStatus;
    @FXML
    private Rectangle progressBar;
    @FXML
    private Rectangle destinationBar;
    @FXML
    private ImageView busIcon;

    @FXML
    private Pane paneGetLineInfor;

    @FXML
    private Pane paneSearchLines;

    @FXML
    private Button btnMarkFailed;
    
    @FXML Button btnMarkCompleted;

    private static BusLine passedBusLine;
    private static model.Company companyAssigned;
    private static model.Bus bus;

    private BusAnimation busAnimation;
    private static final double BUS_MAX_POSITION = 550;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Session.getUser().getRole() == Role.USER ){
            paneGetLineInfor.getChildren().remove(btnMarkFailed);
            paneGetLineInfor.getChildren().remove(btnMarkCompleted);
        }

        Translate.translateForAllPanes(paneSearchLines);
        companyAssigned = passedBusLine.getCompanyAssigned();
        bus = passedBusLine.getBusModel();
        setData();
        setInitialBusAnimationState();
    }

    public static void passBusLine(BusLine bl) {
        passedBusLine =bl;
    }

    private void setData() {
        txtBusLineId.setText(passedBusLine.getLineId());
        txtPlaceFrom.setText(passedBusLine.getStartLocation());
        txtPlaceTo.setText(passedBusLine.getEndLocation());
        txtTimeFrom.setText(passedBusLine.getStartTime().getHour() + ":" + passedBusLine.getStartTime().getMinute());
        txtTimeTill.setText(passedBusLine.getEndTime().getHour() + ":" + passedBusLine.getEndTime().getMinute());
        txtDate.setText(passedBusLine.getStartTime().getMonthValue() + "/" + passedBusLine.getStartTime().getDayOfMonth() + "/" + passedBusLine.getStartTime().getYear());
        txtCompanyAssigned.setText(companyAssigned.getCompanyName());
        txtBusModel.setText(bus.getBusModel());
        txtComfortRating.setText(ComfortRating.getTxtComfortRating(bus.getComfortRating()));
        txtPassangerCapacity.setText(String.valueOf(bus.getPassangerCapacity()));
        txtTotalStops.setText("0");
        txtActivityStatus.setText(passedBusLine.getStatus().name());
        setActivityColor();
    }

    private void setActivityColor() {
        switch (txtActivityStatus.getText()) {
            case "ACTIVE":
                txtActivityStatus.setStyle("-fx-text-fill: #00b61f;");
                break;
            case "COMPLETED":
                txtActivityStatus.setStyle("-fx-text-fill: #ffa51b;");
                break;
            default:
                txtActivityStatus.setStyle("-fx-text-fill: #ff0000;");
        }
    }

    private void setInitialBusAnimationState() {
        LocalDateTime now = LocalDateTime.now();
        busAnimation = new BusAnimation(busIcon, BUS_MAX_POSITION);

        if (txtActivityStatus.getText().equals("ACTIVE")) {
            LocalDateTime startTime = passedBusLine.getStartTime();
            LocalDateTime endTime = passedBusLine.getEndTime();


            Duration totalDuration = Duration.between(startTime, endTime);
            Duration elapsedTime = Duration.between(startTime, now);

            double percentageCompleted = (double) elapsedTime.toMinutes() / totalDuration.toMinutes();

            percentageCompleted = Math.max(0, Math.min(percentageCompleted, 1));

            double maxProgressBarWidth = 630;
            double newProgressBarWidth = percentageCompleted * maxProgressBarWidth;
            progressBar.setWidth(newProgressBarWidth);


            double newBusIconPosition = percentageCompleted * BUS_MAX_POSITION;
            busIcon.setTranslateX(newBusIconPosition);
            busAnimation.startAnimation(passedBusLine.getStartTime(), passedBusLine.getEndTime(), now);
        }
        else if (txtActivityStatus.getText().equals("COMPLETED")) {
            busIcon.setTranslateX(BUS_MAX_POSITION);
            progressBar.setWidth(630);
        }
        else if(txtActivityStatus.getText().equals("FAILED")){
            busIcon.setTranslateX(0);
            progressBar.setWidth(30);
        }
    }

    public void handleFailLine(ActionEvent actionEvent) {
        if (!txtActivityStatus.getText().equals("ACTIVE")){
            showError("Cannot mark as failed", "Already marked as completed or failed");
            return;
        }
        txtActivityStatus.setText("FAILED");
        try {
            BusLineService.updateBusLineStatus(passedBusLine, Status.FAILED);

        }catch (RuntimeException re){
            showError("Failed to update", re.getMessage());
        }
        Navigator.navigate(actionEvent, Navigator.HOME_PAGE);
    }

    public void handleCompleteLine(ActionEvent actionEvent){
        if (!txtActivityStatus.getText().equals("ACTIVE")){
            showError("Cannot mark as completed", "Already marked as completed or failed");
            return;
        }
        txtActivityStatus.setText("COMPLETED");
        try {
            BusLineService.updateBusLineStatus(passedBusLine, Status.COMPLETED);

        }catch (RuntimeException re){
            showError("Failed to update", re.getMessage());
        }
        Navigator.navigate(actionEvent, Navigator.HOME_PAGE);

    }


}
