package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ComponentCompanySearch {
    @FXML
    private Label txtCompanyName;
    @FXML
    private Label txtCompanyId;
    @FXML
    private Label txtAreaCode;
    @FXML
    private Label txtActivity;

    public void setData(model.Company company) {
        txtCompanyName.setText(company.getCompanyName());
        txtCompanyId.setText(company.getCompanyId());
        txtAreaCode.setText(company.getAreaCode().toString());
        txtActivity.setText(company.getCompanyStatus().name());
    }

    public void handleManage(ActionEvent actionEvent) {
    }
}
