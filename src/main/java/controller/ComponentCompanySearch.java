package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import service.CompanyService;
import service.Translate;

public class ComponentCompanySearch {
    @FXML
    private Label txtCompanyNameSearchCompany;
    @FXML
    private Label txtCompanyId;
    @FXML
    private Label txtAreaCode;
    @FXML
    private Label txtActivity;

    @FXML
    private Pane paneSearchCompanyComponent;

    public void setData(model.Company company) {
        Translate.translateForAllPanes(paneSearchCompanyComponent);
        txtCompanyNameSearchCompany.setStyle("-fx-font-weight: bold");
        txtCompanyNameSearchCompany.setText(company.getCompanyName());
        txtCompanyId.setStyle("-fx-font-weight: bold");
        txtCompanyId.setText(company.getCompanyId());
        txtAreaCode.setStyle("-fx-font-weight: bold");
        txtAreaCode.setText(company.getAreaCode().toString());
        txtActivity.setStyle("-fx-font-weight: bold");
        txtActivity.setText(company.getCompanyStatus().name());
        if(txtActivity.getText().equals("ACTIVE")){
            txtActivity.setStyle("-fx-text-fill:  #1DB954;");
        }else{
            txtActivity.setStyle("-fx-text-fill: #ef233c;");
        }
    }

    public void handleManage(ActionEvent actionEvent) {

        Company.passCompany(CompanyService.getCompanyFromId(txtCompanyId.getText()));
        Navigator.navigate(actionEvent, Navigator.COMPANY_PAGE);
    }
}
