package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Company extends BGmain{

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private TextField txtLineID;

    @FXML
    private DatePicker dateSearchDate;

    @FXML
    private MenuButton menuStatus;

    @FXML
    private MenuButton menuAreaCode;



    public void handleManageBuses(ActionEvent event) {

    }


    public void handleStatus(ActionEvent actionEvent) {
    }

    public void handleAddBus(ActionEvent actionEvent) {
    }

    public void handleSuspend(ActionEvent actionEvent) {
    }

    public void handleAreaCode(ActionEvent actionEvent) {
    }

    public void handleFilter(ActionEvent actionEvent) {
    }
}
