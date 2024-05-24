package controller;

import app.Navigator;
import app.Session;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import model.Bus;
import model.BusLine;
import model.filter.BusLineFilter;
import model.filter.BusLineFilter;
import service.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.stream.Collectors;

public class Dashboard extends BGmain implements Initializable {

    @FXML
    private MenuButton menuStatus;

    @FXML
    private Label txtName;

    @FXML
    private AnchorPane paneAddCompanyLine;

    @FXML
    private TextField txtFrom;

    @FXML
    private TextField txtTo;

    @FXML
    protected Pane paneSearchLines;

    @FXML
    protected Button btnGoToProfile;

    @FXML
    private TextField txtCompany;


    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    private String checkActivity= null;

    private LocalDateTime localDateTimeFrom;
    private LocalDateTime localDateTimeTo;




    @FXML
    public void handleStatusMenuItemClicked(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuStatus.setText(menuItem.getText());
        checkActivity = menuItem.getText();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            super.initialize(url, resourceBundle);
            Translate.translateForAllPanes(paneSearchLines);
            dateTo.setValue(LocalDate.from(LocalDateTime.now()));
            handleSearch(new ActionEvent());
             txtName.setText(Session.getUser().getUsername());

         }catch (NullPointerException e){


        }

    }




    public void handleSearch(ActionEvent event) {
        double totalHeight = 0;
        List<BusLine> busLine = BusLineService.getAllBusLines(new BusLineFilter(txtFrom.getText(),txtTo.getText(),checkActivity,txtCompany.getText(),DateConversion.localDate(dateFrom.getValue(),"05:00:00"),DateConversion.localDate(dateTo.getValue(),"08:00:00")));
        System.out.println(busLine.size());
        paneAddCompanyLine.getChildren().clear();
        try {
            for (BusLine bus : busLine) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/busLineComponent.fxml"));
                AnchorPane busPane = loader.load();
                ComponentBusLine addLineController = loader.getController();

                addLineController.setData(bus.getStartLocation(), bus.getEndLocation(), bus.getStatus().toString(), bus.getStartTime(), bus.getEndTime());
                addLineController.passBusLine(bus);
                busPane.setLayoutY(totalHeight);

                paneAddCompanyLine.getChildren().add(busPane);

                totalHeight += busPane.getPrefHeight() + 5;
            }


            ScrollPane scrollPane = new ScrollPane(paneAddCompanyLine);
            scrollPane.setFitToWidth(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        paneAddCompanyLine.setPrefHeight(totalHeight);

    }





    public void handleGotToProfile(ActionEvent event) {
        Navigator.navigate(event,Navigator.PROFILE_PAGE);
    }
}
