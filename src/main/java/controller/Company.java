package controller;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.BusLine;
import model.filter.BusLineFilter;
//import model.filter.CompanyBusLineFilter;
import repository.CompanyRepository;
import service.BusLineService;
import service.DateConversion;
import service.CompanyService;
import service.Translate;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class Company extends BGmain implements Initializable {

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtLineID;

    @FXML
    private DatePicker dateSearchDate;
    @FXML
    private Label labelStatus;

    @FXML
    private Label labelAreaCode;

    @FXML
    private MenuButton mbtnAreaCode;
    @FXML
    private Button btnSuspend;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnEdit;
    @FXML
    private Label labelCompanyId;
    @FXML
    private Pane paneCompanyReport;

    @FXML
    private Pane paneEntireCompany;

    @FXML
    private Pane paneGetSpecifficLines;

    @FXML

    private static model.Company company;

    private int editing = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Translate.translateForAllPanes(paneCompanyReport);
        Translate.translateForAllPanes(paneEntireCompany);
        txtCompanyName.setStyle("-fx-font-weight: bold;-fx-background-color: #2b2d42;-fx-text-fill: white; -fx-background-radius: 0px");
        txtCompanyName.setEffect(new DropShadow());
        txtCompanyName.setText(company.getCompanyName());

        txtAreaDescription.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-font-weight: bold;-fx-control-inner-background:#2b2d42; -fx-border-insets: 0px;-fx-background-radius: 0px ");
        txtAreaDescription.setEffect(new DropShadow());
        txtAreaDescription.setText(company.getDescription());
        labelStatus.setText(company.getCompanyStatus().name());
        labelAreaCode.setText(company.getAreaCode().name());
        mbtnAreaCode.setText(company.getAreaCode().name());
        labelCompanyId.setText("ID: " + company.getCompanyId());
        updateStatusManagerView();
        loadCompanyLines();
    }


    public void handleManageBuses(ActionEvent event) {
        SearchBus.passCompany(company);
        Navigator.navigate(event, Navigator.SEARCH_BUS);

    }

    public void handleAddBus(ActionEvent actionEvent) {
        AddBus.passData(company);
        Navigator.navigate(actionEvent, Navigator.ADD_BUS_PAGE);
    }


    public void handleAreaCode(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        mbtnAreaCode.setText(item.getText());
        labelAreaCode.setText(mbtnAreaCode.getText());
    }

    public void handleFilter(ActionEvent actionEvent) {
        loadCompanyLines();
    }

    public void handleEdit(ActionEvent actionEvent) {
        switch (editing) {
            case 0:
                txtCompanyName.setEditable(true);
                txtAreaDescription.setEditable(true);
                txtCompanyName.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                txtAreaDescription.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                labelAreaCode.setVisible(false);
                mbtnAreaCode.setVisible(true);
                btnSave.setDisable(false);
                btnSave.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-weight: bold;-fx-background-radius: 0");
                editing = 1;
                btnEdit.setText("Cancel");
                break;
            case 1:
                txtCompanyName.setEditable(false);
                txtAreaDescription.setEditable(false);
                txtCompanyName.setFocusTraversable(false);
                txtCompanyName.setStyle("-fx-font-weight: bold;-fx-background-color: #2b2d42;-fx-text-fill: white; -fx-background-radius: 0px");
                txtAreaDescription.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent; -fx-font-weight: bold;-fx-control-inner-background:#2b2d42; -fx-border-insets: 0px;-fx-background-radius: 0px ");
                labelAreaCode.setVisible(true);
                mbtnAreaCode.setVisible(false);
                btnSave.setDisable(true);
                btnEdit.setText("Edit");
                btnSave.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: rgba(211,211,211,0.8)");

                editing = 0;
        }
    }


    public void handleSave(ActionEvent actionEvent) {
        if (txtCompanyName.getText().trim().equals(company.getCompanyName()) && txtAreaDescription.getText().trim().equals(company.getDescription()) && labelAreaCode.getText().trim().equals(company.getAreaCode().name())) {
            showError("Nothing has changed", "nothing to save here");
            return;
        }
        if (txtCompanyName.getText().trim().isEmpty()) {
            showError("Invalid company name", "The company name cannot be empty");
            return;
        }
        if (txtAreaDescription.getText().trim().isEmpty()) {
            showError("Invalid company description", "The company description cannot be empty");
            return;
        }
        company.setCompanyName(txtCompanyName.getText().trim());
        company.setDescription(txtAreaDescription.getText().trim());
        company.setAreaCode(AreaCode.valueOf(mbtnAreaCode.getText()));
        try {
            if(CompanyService.updateCompany(company)){
                showConfirmation("Company changes saved", "The process was a success");
                handleEdit(new ActionEvent());
            }
        } catch (RuntimeException re) {
            showError("Error updating company", re.getMessage());
        }
    }

    public static void passCompany(model.Company cmp) {
        // this method servers to pass the company from the search company scene
        company = cmp;
    }

    public void handleStatusManager(ActionEvent actionEvent) {
        switch (company.getCompanyStatus()){
            case ACTIVE :
                            company.setCompanyStatus(ActivityStatus.SUSPENDED);
                            labelStatus.setText("Suspended");
                            updateStatusManagerView();
                            CompanyService.updateCompany(company);
                            showConfirmation("Coompany Suspended", "The activities of this company have been suspended");
                            Navigator.navigate(actionEvent,Navigator.SEARCH_COMPANY_PAGE);

                break;
            case SUSPENDED:
                            company.setCompanyStatus(ActivityStatus.ACTIVE);
                            labelStatus.setText("Active");
                            updateStatusManagerView();
                            CompanyService.updateCompany(company);
                            showConfirmation("Company Activated", "The activities of this company have been activated");
                            Navigator.navigate(actionEvent,Navigator.SEARCH_COMPANY_PAGE);

                            break;
        }

    }

    private void updateStatusManagerView() {
        // this method updates the button so it corresponds to the activity status
        switch (company.getCompanyStatus()) {
            case ACTIVE:
                btnSuspend.setText("Suspend");
                btnSuspend.setStyle("-fx-background-color:  #ef233c; -fx-font-weight: bold");
                break;
            case SUSPENDED:
                btnSuspend.setText("Activate");
                btnSuspend.setStyle("-fx-background-color:   #1DB954; -fx-font-weight: bold");
                break;
        }
    }


    public void loadCompanyLines() {
        double totalHeight =0;
        System.out.println(txtCompanyName.getText());
        List<BusLine> busLine = BusLineService.getAllBusLines(new BusLineFilter(txtCompanyName.getText(),txtLineID.getText(), DateConversion.localDate(dateSearchDate.getValue(),"12:59:59")));
        System.out.println(busLine.size());
        paneGetSpecifficLines.getChildren().clear();
        try {
            for (BusLine bus : busLine) {
                System.out.println("1");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/busLineComponent.fxml"));
                AnchorPane busPane = loader.load();
                ComponentBusLine addLineController = loader.getController();

                addLineController.setData(bus.getStartLocation(), bus.getEndLocation(), bus.getStatus().toString(), bus.getStartTime(), bus.getEndTime());
                addLineController.passBusLine(bus);
                busPane.setLayoutY(totalHeight);

                paneGetSpecifficLines.getChildren().add(busPane);

                totalHeight += busPane.getPrefHeight() + 5;
            }


            ScrollPane scrollPane = new ScrollPane(paneGetSpecifficLines);
            scrollPane.setFitToWidth(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        paneGetSpecifficLines.setPrefHeight(totalHeight);


//    }


    }
}
