package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Company;
import service.CompanyService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCompany extends BGmain implements Initializable {

    public Button btnAddCompany;
    @FXML
    protected TextField txtCompanyName;

    @FXML
    protected Pane paneSearchCompany;
    @FXML
    protected VBox paneQueryResult;

    @FXML
    private ScrollPane paneSearchCompanyScrollPane;


    public void handleSearch(ActionEvent actionEvent) {
        String companyName = txtCompanyName.getText().trim();
        try {
            if (companyName.trim().isEmpty()) {
                showError("Search Error", "Company name is required.");

            }
            List<Company> companies = CompanyService.searchCompanies(companyName);
            displayCompanies(companies);
        }
        catch (RuntimeException re){
            showError("Search error", re.getMessage());
        }
    }

    public void handleAddCompany(ActionEvent actionEvent) {
        Navigator.navigate(actionEvent , Navigator.CREATE_COMPAMY);
    }



    private void displayCompanies(List<model.Company> companies) {
        double totalHeight=0;
        paneQueryResult.getChildren().clear();
        for (model.Company company : companies) {
            try {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/searchCompanyComponent.fxml"));
                Pane companyPane = loader.load();
                ComponentCompanySearch companyController = loader.getController();
                companyController.setData(company);
                paneQueryResult.getChildren().add(companyPane);

                totalHeight += companyPane.getPrefHeight() + paneQueryResult.getSpacing();

            } catch (IOException e) {
                showError("Error displaying companies", e.getMessage());
            }
            paneQueryResult.setPrefHeight(totalHeight);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCompanies(CompanyService.loadAllCompanies());
    }
}
