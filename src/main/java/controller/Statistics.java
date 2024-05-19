package controller;

import ENUMS.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;
import model.AreaCodeStatistic;
import service.CompanyService;
import service.StatisticsService;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics extends BGmain {
    @FXML
    private TextField txtCompanyName;
    @FXML
    private MenuButton mbtnReportType;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart pieLineReport;
    @FXML
    private Label txtLineReportPeak;
    @FXML
    private Label txtLineReportMin;
    @FXML
    private Label txtLineReportAverage;
    @FXML
    private Label txtLineReportTotalLines;
    @FXML
    private Label txtLineReportCompletedLines;
    @FXML
    private Label txtLineReportActiveLines;
    @FXML
    private Label txtLineReportFailedLines;
    @FXML
    private AnchorPane paneLineReportDisplay;
    @FXML
    private AnchorPane paneAreaReportDisplay;
    @FXML
    private MenuButton mbtnTimeDistance;
    @FXML
    private VBox vboxAreaReportContainer;


    private List<model.Company> companies = CompanyService.loadAllCompanies();
    private ObservableList<String> suggestions = FXCollections.observableArrayList();
    private ListView<String> suggestionListView = new ListView<>(suggestions);
    private Popup suggestionPopup = new Popup();
    private AnchorPane previousPane = null;

    private int getTimeDistance(){
        switch (mbtnTimeDistance.getText()){
            case "Weekly": return 1;
            case "Bi-Weekly": return 2;
            case "Monthly" : return 4;
            case "Yearly" : return 48;
            case "All-Time": return 39996; // if this somehow made it to year 2858 im sorry for the error
            default: return 39996;
        }
    }

    @FXML
    public void initialize() {
        txtCompanyName.addEventHandler(KeyEvent.KEY_RELEASED, event -> updateSuggestions());

        suggestionPopup.getContent().add(suggestionListView);
        suggestionPopup.setAutoHide(true);

        suggestionListView.setOnMouseClicked(event -> {
            String selectedCompany = suggestionListView.getSelectionModel().getSelectedItem();
            if (selectedCompany != null) {
                txtCompanyName.setText(selectedCompany);
                suggestionPopup.hide();
            }
        });
    }

    public void handleCreateReport(ActionEvent actionEvent) {
        if (txtCompanyName.getText().isEmpty()){
            showError("Please select a company" , null);
            return;
        }
        if (previousPane != null)
            previousPane.setVisible(false);
        switch (mbtnReportType.getText()){
            case "Line-Report": showLineReport(); previousPane = paneLineReportDisplay; return;
            case "Area-Report": showAreaReport(getTimeDistance()); previousPane = paneAreaReportDisplay; return;
            default: showError("Please select a valid report", null);
        }

    }

    private void updateSuggestions() {
        String query = txtCompanyName.getText().toLowerCase();
        if (query.isEmpty()) {
            suggestionPopup.hide();
            return;
        }

        List<String> filteredNames = companies.stream()
                .map(model.Company::getCompanyName)
                .filter(name -> name != null && name.toLowerCase().contains(query))
                .sorted()
                .collect(Collectors.toList());

        if (filteredNames.isEmpty()) {
            suggestionPopup.hide();
        } else {
            suggestions.setAll(filteredNames);
            if (!suggestionPopup.isShowing()) {
                Window window = txtCompanyName.getScene().getWindow();
                suggestionPopup.show(window,
                        window.getX() + txtCompanyName.localToScene(0, 0).getX() + txtCompanyName.getScene().getX(),
                        window.getY() + txtCompanyName.localToScene(0, 0).getY() + txtCompanyName.getScene().getY() + txtCompanyName.getHeight()
                );
            }
        }
    }

    public void handleReportType(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        mbtnReportType.setText(item.getText());
    }

    private void generateBarChart() {
        barChart.getData().clear();
        String companyName = txtCompanyName.getText().trim();
        Map<LocalDate, Long> data = StatisticsService.getLinesCompletedLastMonth(companyName,getTimeDistance());

        if (data.isEmpty()) {
            showError("No data found for this company" , "Check if name is valid and data exists");
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Lines Completed");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

        data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    series.getData().add(new XYChart.Data<>(entry.getKey().format(formatter), entry.getValue()));
                });

        barChart.getData().add(series);
        List<XYChart.Data> labelData = new ArrayList<>(series.getData());
        List<Double> dataValues = new ArrayList<>();
        for (XYChart.Data item : labelData) {
            dataValues.add((Double) item.getYValue());
        }
        Collections.sort(dataValues);
        txtLineReportPeak.setText("Maximum lines: \n" + String.valueOf(dataValues.get(dataValues.size() -1).intValue()));
        txtLineReportMin.setText("Minimum lines: \n" + String.valueOf(dataValues.get(0).intValue()));
        OptionalDouble average = dataValues
                .stream()
                .mapToDouble(a -> a)
                .average();
        txtLineReportAverage.setText("Average lines: \n" + String.valueOf(average.getAsDouble()).substring(0,3));


        barChart.setVisible(true);
    }
    private void generatePieChart() {


        pieLineReport.getData().clear();
        String companyName = txtCompanyName.getText().trim();
        Map<Status, Integer> pieData = StatisticsService.getSuccesPie(companyName, getTimeDistance());
        if (pieData.isEmpty()) {
            showError("No data found for this company" , "Check if name is valid and data exists");
            return;
        }
        for (Map.Entry<Status, Integer> entry : pieData.entrySet()) {
            Status status = entry.getKey();
            Integer value = entry.getValue();
            PieChart.Data slice = new PieChart.Data(status.name(), value);
            pieLineReport.getData().add(slice);
        }
        txtLineReportTotalLines.setText("Total Lines: " +(pieLineReport.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum()));
        txtLineReportCompletedLines.setText("Completed Lines: " +(pieLineReport.getData().stream().filter(data -> data.getName().equals("COMPLETED")).mapToDouble(PieChart.Data::getPieValue).sum()));
        txtLineReportActiveLines.setText("Active Lines: " +(pieLineReport.getData().stream().filter(data -> data.getName().equals("ACTIVE")).mapToDouble(PieChart.Data::getPieValue).sum()));
        txtLineReportFailedLines.setText("Failed Lines: " +(pieLineReport.getData().stream().filter(data -> data.getName().equals("FAILED")).mapToDouble(PieChart.Data::getPieValue).sum()));
        pieLineReport.setVisible(true);
    }

    private void showLineReport(){
        paneLineReportDisplay.setVisible(true);
        generateBarChart();
        generatePieChart();
    }

    public void showAreaReport(int weeks) {
        paneAreaReportDisplay.setVisible(true);
        try {
            List<AreaCodeStatistic> statistics = StatisticsService.getAreaCodeStatisticList(weeks);
            double totalHeight = 0;
            vboxAreaReportContainer.getChildren().clear();


            for (AreaCodeStatistic statistic : statistics) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/components/statisticsAreaReportComponent.fxml"));
                    Pane statisticPane = loader.load();
                    ComponentAreaReport controller = loader.getController();
                    controller.setData(statistic);
                    vboxAreaReportContainer.getChildren().add(statisticPane);

                    totalHeight += statisticPane.getPrefHeight() + vboxAreaReportContainer.getSpacing();

                } catch (IOException e) {
                    showError("Error displaying statistics", e.getMessage());
                }

                vboxAreaReportContainer.setPrefHeight(totalHeight);
            }
        }
            catch(RuntimeException re){
                showError("Error showing area report", re.getMessage());
            }
    }

    public void handleTimeDistance(ActionEvent actionEvent) {
        MenuItem item =(MenuItem) actionEvent.getSource();
        mbtnTimeDistance.setText(item.getText());
    }
}
