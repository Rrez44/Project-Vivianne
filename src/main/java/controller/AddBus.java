package controller;

import ENUMS.ActivityStatus;
import ENUMS.BusType;
import ENUMS.ComfortRating;
import INTERFACES.Identifiable;
import INTERFACES.StarManager;
import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import model.Company;
import service.BusService;
import service.Translate;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBus extends BGmain  implements StarManager, Initializable, Identifiable {

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
    private TextField txtVin;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtPassengerCapacity;

    private static Company passedCompany;

    @FXML
    private Pane paneAddBusLabel;

    @FXML
    private Pane paneAddBus;


    private int num = 0;
    private int cRating = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Translate.translateForAllPanes(paneAddBus);
        Translate.translateForAllPanes(paneAddBusLabel);

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



    public void changeColors(int num){
        SVGPath[] svgStars={svgStar1,svgStar2,svgStar3,svgStar4,svgStar5};

        for(int i=0;i< svgStars.length;i++){

            if(i<num){
                svgStars[i].setFill(Color.GOLD);
                cRating = num;
            }else{
                svgStars[i].setFill(Color.WHITE);
            }

        }
    }


    public void handleCreate(ActionEvent actionEvent) {
        if (!isValidVIN(txtVin.getText().trim())) {
            showError("Validation Error", "VIN is invalid.");
            return;
        }
        if (txtModel.getText().trim().isEmpty()) {
            showError("Validation Error", "Model cannot be empty.");
            return;
        }
        if (txtPassengerCapacity.getText().trim().isEmpty()) {
            showError("Validation Error", "Passenger Capacity cannot be empty.");
            return;
        }
        if (cRating == 0){
            showError("Validation Error", "Please choose a comfort rating");
            return;
        }
        if (mbtnBusType.getText().equals("Bus Type")){
            showError("Validation Error", "Please choose a bus type");
        }
        try {
            int pCapacity = Integer.parseInt(txtPassengerCapacity.getText().trim());
            if (pCapacity <= 0 ) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showError("Validation Error", "Passenger Capacity must be a valid number.");
            return;
        }

        try {
            BusService.createBus(generateId(),txtModel.getText().trim(), txtVin.getText(), Integer.parseInt(txtPassengerCapacity.getText().trim()), BusType.valueOf(mbtnBusType.getText()), ActivityStatus.ACTIVE, ComfortRating.fromInt(cRating), passedCompany);
        }
        catch (RuntimeException re){
            showError("Error creating new bus", re.getMessage());
            return;
        }
        showConfirmation("Success", "Bus successfully created and added to company: "+ passedCompany.getCompanyName());
        handleDiscard(new ActionEvent());
    }

    private boolean isValidVIN(String vin) {
        //  i dont know wtf that regex does but it works
        return vin.length() == 17 && vin.matches("^[A-HJ-NPR-Z0-9]+$");
    }
    public void handleDiscard(ActionEvent actionEvent) {
        Navigator.goBack(actionEvent);
    }

    public void handleBusType(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        mbtnBusType.setText(item.getText());
    }
    public static void passData(Company company){
        // this method will be used before switching the scene so you can pass
        // the company as a parameter to the new scene
        passedCompany = company;
    }
}
