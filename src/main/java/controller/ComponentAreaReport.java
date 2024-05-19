package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.AreaCodeStatistic;

public class ComponentAreaReport {
    @FXML
    private Label lblAreaCode;
    @FXML
    private Label lblTotalLines;
    @FXML
    private Label lblSuccessRate;
    @FXML
    private Label lblHoursTraveled;

    public void setData(AreaCodeStatistic statistic) {
        lblAreaCode.setText(statistic.getAreaCode().toString());
        lblTotalLines.setText(statistic.getTotalLines());
        lblSuccessRate.setText(statistic.getSuccessRate());
        lblHoursTraveled.setText(statistic.getHoursTraveled());
    }
}
