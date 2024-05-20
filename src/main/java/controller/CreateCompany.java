package controller;

import ENUMS.AreaCode;
import INTERFACES.Identifiable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import repository.CompanyRepository;
import service.ClearForm;
import service.Translate;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateCompany extends BGmain  implements Identifiable, Initializable {

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private MenuButton mbtnAreaCode;

    @FXML
    private Pane paneCreateCompany;

    @FXML
    private Pane paneCreateCompanyLabel;

    @FXML
    private void handleAreaCodeMenuItemClicked(ActionEvent ae){
        MenuItem item = (MenuItem) ae.getSource();
        mbtnAreaCode.setText(item.getText());
    }

//    @FXML
//    private void handleDiscard(ActionEvent event) {
//        txtCompanyName.setText("");
//        txtDescription.setText("");
//
//        mbtnAreaCode.setText("PRISTINA");
//    }

    @FXML
    private void handleDiscard() {
        ClearForm.clearFormInputs(paneCreateCompany);
//        txtCompanyName.setText("");
//        txtDescription.setText("");
        mbtnAreaCode.setText("PRISTINA");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Translate.translateForAllPanes(paneCreateCompany);
        Translate.translateForAllPanes(paneCreateCompanyLabel);

    }

    @FXML
    private void handleCreate(ActionEvent event) {
        if (createCompany())
            showConfirmation("Company Alert" ,"The process was a success" );
        handleDiscard();
    }

    private boolean createCompany() {
        String companyName = txtCompanyName.getText();
        String description = txtDescription.getText();
        String areaCode = mbtnAreaCode.getText();
        try {
            if (companyName == null || companyName.trim().isEmpty()) {
                showError("Validation Error", "Company name is required.");
                return false;
            }

            if (description == null || description.trim().isEmpty()) {
                showError("Validation Error", "Description is required.");
                return false;
            }
            CompanyRepository.createCompany(companyName, AreaCode.valueOf(areaCode),description);

        }
        catch (RuntimeException e){
            showError("Create Company Error", e.getMessage());
        }
        return true;
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {

//    }
}
