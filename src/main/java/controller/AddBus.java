package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
                break;
            case 2:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.WHITE);
                svgStar4.setFill(Color.WHITE);
                svgStar5.setFill(Color.WHITE);
                break;
            case 3:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.WHITE);
                svgStar5.setFill(Color.WHITE);
                break;
            case 4:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.GOLD);
                svgStar5.setFill(Color.WHITE);
                break;
            case 5:
                svgStar1.setFill(Color.GOLD);
                svgStar2.setFill(Color.GOLD);
                svgStar3.setFill(Color.GOLD);
                svgStar4.setFill(Color.GOLD);
                svgStar5.setFill(Color.GOLD);
                break;
        }

    }


    public void handleAddBus(ActionEvent event) {
    }
}
