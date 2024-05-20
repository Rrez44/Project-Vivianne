package service;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslateRecords {

        public static void translateFormInputs(String language,Pane pane){
            if(language.equals("English")) {
                translateForm("en", pane);
            }else {
                translateForm("sq", pane);
            }
        }



        private static void translateForm(String language,Pane pane) {
            Locale locale = new Locale(language);
            ResourceBundle bundle = ResourceBundle.getBundle("translations.content", locale);
            try {
                for (Node node : pane.getChildren()) {
                    if (node.getId() != null) {
                        if (node instanceof Pane) {
                            translateForm(language, (Pane) node);
                        } else if (node instanceof TextInputControl) {
                            ((TextInputControl) node).setPromptText(bundle.getString(node.getId()));
                        } else if (node instanceof Button) {
                            ((Button) node).setText(bundle.getString(node.getId()));
                        }else if(node instanceof MenuButton) {
                            ((MenuButton) node).setText(bundle.getString(node.getId()));
                        }else if(node instanceof Label){
                            if(node.getId().equals("txtName")){
                            }else{
                                ((Label) node).setText(bundle.getString(node.getId()));

                            }
                        }
                    }
                }
            }catch (NullPointerException e) {

            }

        }


}
