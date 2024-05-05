package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    private static model.Company company;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCompanyName.setText(company.getCompanyName());
        txtAreaDescription.setText(company.getDescription());
        labelStatus.setText(company.getCompanyStatus().name());
        labelAreaCode.setText(company.getAreaCode().toString());
    }


    public void handleManageBuses(ActionEvent event) {

    }
    public void handleAddBus(ActionEvent actionEvent) {
    }

    public void handleSuspend(ActionEvent actionEvent) {
    }

    public void handleAreaCode(ActionEvent actionEvent) {
    }

    public void handleFilter(ActionEvent actionEvent) {
    }

    public void handleEdit(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public static void passCompany(model.Company cmp){
        // this method servers to pass the company from the search company scene
        company = cmp;
    }

}
