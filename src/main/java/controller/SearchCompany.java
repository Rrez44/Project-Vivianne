package controller;

import app.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Company;
import repository.CompanyRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCompany extends BGmain implements Initializable {

    @FXML
    protected TextField txtCompanyName;

    @FXML
    protected Pane paneSearchCompany;
    @FXML
    protected VBox paneQueryResult;


    public void handleSearch(ActionEvent actionEvent) {
        String companyName = txtCompanyName.getText().trim();
        try {
            if (companyName.trim().isEmpty()) {
                showError("Search Error", "Company name is required.");

            }
            List<Company> companies = CompanyRepository.searchCompanies(companyName);
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
        paneQueryResult.getChildren().clear();
        for (model.Company company : companies) {
            try {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/searchCompanyComponent.fxml"));
                Pane companyPane = loader.load();
                ComponentCompanySearch companyController = loader.getController();
                companyController.setData(company);

                paneQueryResult.getChildren().add(companyPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCompanies(CompanyRepository.initialLoad());
    }
}
