package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import service.CompanyService;
import service.StatisticsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics extends BGmain {
    @FXML
    private TextField txtCompanyName;
    @FXML
    private MenuButton mbtnReportType;
    @FXML
    private BarChart<String, Number> barChart;

    private List<model.Company> companies = CompanyService.loadAllCompanies();
    private ObservableList<String> suggestions = FXCollections.observableArrayList();
    private ListView<String> suggestionListView = new ListView<>(suggestions);
    private Popup suggestionPopup = new Popup();

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
        barChart.setVisible(true);
        generateChart();
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

    private void generateChart() {
        barChart.getData().clear();
        String companyName = txtCompanyName.getText();
        Map<LocalDate, Long> data = StatisticsService.getLinesCompletedLastMonth(companyName);

        if (data == null || data.isEmpty()) {
            System.out.println("No data found for the company: " + companyName);
            return;
        }

        data.forEach((date, count) -> System.out.println("Date: " + date + ", Count: " + count));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Lines Completed");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    series.getData().add(new XYChart.Data<>(entry.getKey().format(formatter), entry.getValue()));
                });

        barChart.getData().add(series);
    }
}
