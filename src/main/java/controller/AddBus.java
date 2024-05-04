package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import model.Company;
import org.w3c.dom.events.MouseEvent;

public class AddBus extends BGmain {

    @FXML
    private SVGPath svgStar1;
    @FXML
    private SVGPath svgStar2;
    @FXML
    private SVGPath svgStar3;
    @FXML
    private SVGPath svgStar4;
    @FXML
    private SVGPath svgStar5;
    @FXML
    private MenuButton mbtnBusType;

    @FXML
    private TextField txtVin;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtPassengerCapacity;

    private Company passedCompany;


    private int num = 0;

    public void handleFillStar1(ActionEvent event) {
        changeColors(1);
    }
    public void handleFillStar2(ActionEvent event) {
        changeColors(2);
    }
    public void handleFillStar3(ActionEvent event) {
        changeColors(3);
    }

    public void handleFillStar4(ActionEvent event) {
        changeColors(4);
    }



    public void handleFillStar5(ActionEvent event) {
        changeColors(5);
    }


    public void changeColors(int changeIT) {

        switch (changeIT) {
            case 1:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.WHITE);
                svgStar3.setFill(Color.WHITE);
                svgStar4.setFill(Color.WHITE);
                svgStar5.setFill(Color.WHITE);
                num = 1;
                break;

            case 2:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.WHITE);
                svgStar4.setFill(Color.WHITE);
                svgStar5.setFill(Color.WHITE);
                num =2;
                break;
            case 3:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.WHITE);
                svgStar5.setFill(Color.WHITE);
                num = 3;
                break;
            case 4:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.GOLD);
                svgStar5.setFill(Color.WHITE);
                num = 4;
                break;
            case 5:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.GOLD);
                svgStar5.setFill(Color.GOLD);
                num = 5;
                break;
        }

    }



    public void handleCreate(ActionEvent actionEvent) {
        System.out.println(num);
    }

    public void handleDiscard(ActionEvent actionEvent) {
    }

    public void handleBusType(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        mbtnBusType.setText(item.getText());

    }
    public void passData(Company company){
        // this method will be used before switching the scene so you can pass
        // the company as a parameter to the new scene
        this.passedCompany = company;
    }
}
