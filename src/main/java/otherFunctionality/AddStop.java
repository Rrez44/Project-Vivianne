package otherFunctionality;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class AddStop {


    private static int labelCounter =1;
    private final double inputLayoutX =154;
    private static double inputLayoutY =60;
    private final double spacing =30;
    private static double lableLayoutX=70;
    private TextField textField;

    private List<String> stops = new ArrayList<>();
    public void addStop(String inputName, String labelName, AnchorPane addComponentsPane){
        textField = createNewTextField(inputName);
        Label label = createNewLabel(labelName);

        if(!inputName.equals("")){
            updateLayout(textField,label);

            addComponentsPane.setPrefHeight(addComponentsPane.getHeight()+spacing);
            addComponentsPane.getChildren().addAll(textField,label);
        }


    }



    private TextField createNewTextField(String text){
        TextField textField = new TextField(text);
        textField.setId("txtAddStop"+labelCounter);
        textField.setDisable(true);
        stops.add(text);
        return textField;
    }

    private Label createNewLabel(String text){
        Label label = new Label(text + labelCounter);
        label.setId("txtStopName"+labelCounter);
        label.setStyle("-fx-font-weight: bold");
        return label;

    }


    public void updateLayout(TextField textField, Label label){

        textField.setLayoutX(inputLayoutX);
        label.setLayoutX(lableLayoutX);

        inputLayoutY +=spacing;
        textField.setLayoutY(inputLayoutY);
        label.setLayoutY(inputLayoutY);

        labelCounter++;
    }

    public static void restartForm(){
        labelCounter=1;
        inputLayoutY =60;
    }



    public String getTextField(){
        return textField.getText();

    }

    public List<String> getStops(){
        if (stops.isEmpty()){return null;}
        return stops;
    }



}
