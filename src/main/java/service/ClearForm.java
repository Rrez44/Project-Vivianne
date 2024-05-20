package service;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class ClearForm {

    public static void clearFormInputs(Pane pane){
        clearInput(pane);
    }



    private static void clearInput(Pane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof Pane) {
                clearInput((Pane) node);
            } else if (node instanceof TextInputControl) {
                ((TextInputControl) node).clear();
            } else if (node instanceof ChoiceBox) {
                ((ChoiceBox<?>) node).getSelectionModel().clearSelection();
            } else if (node instanceof ComboBox) {
                ((ComboBox<?>) node).getSelectionModel().clearSelection();
            } else if (node instanceof DatePicker) {
                ((DatePicker) node).setValue(null);
            } else if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        }

    }
}
