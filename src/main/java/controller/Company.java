package controller;

import ENUMS.ActivityStatus;
import ENUMS.AreaCode;
import app.Navigator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import repository.CompanyRepository;

import java.net.URL;
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

    private static model.Company company;

    private int editing = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCompanyName.setText(company.getCompanyName());
        txtAreaDescription.setText(company.getDescription());
        labelStatus.setText(company.getCompanyStatus().name());
        labelAreaCode.setText(company.getAreaCode().name());
        txtCompanyName.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        txtAreaDescription.setStyle("-fx-control-inner-background:  #2B2D42; -fx-text-fill: white;");
        mbtnAreaCode.setText(company.getAreaCode().name());
        updateStatusManagerView();
    }


    public void handleManageBuses(ActionEvent event) {

    }
    public void handleAddBus(ActionEvent actionEvent) {
        AddBus.passData(company);
        Navigator.navigate(actionEvent, Navigator.ADD_BUS_PAGE);
    }



    public void handleAreaCode(ActionEvent actionEvent) {
        MenuItem item =(MenuItem) actionEvent.getSource();
        mbtnAreaCode.setText(item.getText());
        labelAreaCode.setText(mbtnAreaCode.getText());
    }

    public void handleFilter(ActionEvent actionEvent) {
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
                editing = 1;
                btnEdit.setText("Cancel");
                break;
            case 1:
                txtCompanyName.setEditable(false);
                txtAreaDescription.setEditable(false);
                txtCompanyName.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                txtAreaDescription.setStyle("-fx-control-inner-background:  #2B2D42; -fx-text-fill: white;");
                labelAreaCode.setVisible(true);
                mbtnAreaCode.setVisible(false);
                btnSave.setDisable(true);
                btnEdit.setText("Edit");
                editing = 0;
        }
    }



    public void handleSave(ActionEvent actionEvent) {
        if(txtCompanyName.getText().trim().equals(company.getCompanyName()) && txtAreaDescription.getText().trim().equals(company.getDescription()) && labelAreaCode.getText().trim().equals(company.getAreaCode().name())){
            showError("Nothing has changed", "nothing to save here");
        }
        if (txtCompanyName.getText().trim().isEmpty()){
            showError("Invalid company name", "The company name cannot be empty");
        }
        if (txtAreaDescription.getText().trim().isEmpty()){
            showError("Invalid company description", "The company description cannot be empty");
        }
        company.setCompanyName(txtCompanyName.getText().trim());
        company.setDescription(txtAreaDescription.getText().trim());
        company.setAreaCode(AreaCode.valueOf(mbtnAreaCode.getText()));
        try {
            if(CompanyRepository.updateCompany(company)){
                showConfirmation("Company changes saved", "The process was a success");
                handleEdit(new ActionEvent());
            }
        }catch (RuntimeException re){
            showError("Error updating company", re.getMessage());
        }
    }

    public static void passCompany(model.Company cmp){
        // this method servers to pass the company from the search company scene
        company = cmp;
    }

    public void handleStatusManager(ActionEvent actionEvent) {
        switch (company.getCompanyStatus()){
            case ACTIVE :
                            company.setCompanyStatus(ActivityStatus.SUSPENDED);
                            labelStatus.setText("Suspended");
                            updateStatusManagerView();
                            CompanyRepository.updateCompany(company);
                            showConfirmation("Coompany Suspended", "The activities of this company have been suspended");

                            break;
            case SUSPENDED:
                            company.setCompanyStatus(ActivityStatus.ACTIVE);
                            labelStatus.setText("Active");
                            updateStatusManagerView();
                            CompanyRepository.updateCompany(company);
                            showConfirmation("Company Activated", "The activities of this company have been activated");
                            break;
        }

    }
    private void updateStatusManagerView(){
        // this method updates the button so it corresponds to the activity status
        switch (company.getCompanyStatus()){
            case ACTIVE : btnSuspend.setText("Suspend"); btnSuspend.setStyle("-fx-background-color:  #ef233c; -fx-font-weight: bold" ); break;
            case SUSPENDED: btnSuspend.setText("Activate"); btnSuspend.setStyle("-fx-background-color:   #1DB954; -fx-font-weight: bold"); break;
        }
    }
}
