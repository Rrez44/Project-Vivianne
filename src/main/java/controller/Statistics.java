package controller;

import ENUMS.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import service.CompanyService;
import service.StatisticsService;

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
    private MenuButton mbtnTimeDistance;

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
        showLineReport();
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

        if (data == null || data.isEmpty()) {
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
        txtLineReportAverage.setText("Average lines: \n" + String.valueOf(average.getAsDouble()).substring(0,4));


        barChart.setVisible(true);
    }
    private void generatePieChart() {

        pieLineReport.getData().clear();
        String companyName = txtCompanyName.getText().trim();
        Map<Status, Integer> pieData = StatisticsService.getSuccesPie(companyName, getTimeDistance());

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

    public void handleTimeDistance(ActionEvent actionEvent) {
        MenuItem item =(MenuItem) actionEvent.getSource();
        mbtnTimeDistance.setText(item.getText());
    }
}
