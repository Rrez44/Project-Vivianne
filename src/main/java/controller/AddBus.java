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



    public void changeColors(int num){
        SVGPath[] svgStars={svgStar1,svgStar2,svgStar3,svgStar4,svgStar5};

        for(int i=0;i< svgStars.length;i++){

            if(i<num){
                svgStars[i].setFill(Color.GOLD);
            }else{
                svgStars[i].setFill(Color.WHITE);
            }

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
