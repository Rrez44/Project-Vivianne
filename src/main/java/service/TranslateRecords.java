package service;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.*;

public class TranslateRecords {

    private static final Set<String> DO_NOT_TRANSLATE = new HashSet<>(Arrays.asList(
            "txtName",
            "labelCompanyId",
            "txtCurrentStatus",
            "txtBusLineId",
            "txtActivityStatus",
            "txtTimeFrom",
            "txtTimeTill",
            "txtCompanyAssigned",
            "txtComfortRating",
            "txtDate",
            "txtBusModel",
            "txtTotalStops",
            "txtPassangerCapacity",
            "txtBusModel",
            "txtVinSearchBus",
            "txtComfortRating",
            "txtActivity",
            "txtCompanyNameSearchCompany",
            "txtCompanyId",
            "txtAreaCode",
            "txtGetStartLocation",
            "txtStart",
            "txtGetEndLocation",
            "txtStatus",
            "txtCompanyNameAddLine",
            "txtBusModelAddLine",
            "txtPassangerSize",
            "txtFirstNameLastNameProfile",
            "txtUsernameProfile",
            "txtEmailProfile",
            "txtRoleProfile",
            "menuTranslate",
            "txtAreaDescription"
    ));


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
                    if (node.getId() != null && !DO_NOT_TRANSLATE.contains(node.getId())) {
                        if (node instanceof Pane) {
                            translateForm(language, (Pane) node);
                        }else if(node instanceof PasswordField){
                            ((PasswordField) node).setPromptText(bundle.getString(node.getId()));
                        }else if(node instanceof TextArea){
                            ((TextArea) node).setText(bundle.getString(node.getId()));
                        }
                        else if (node instanceof TextInputControl) {
                            ((TextInputControl) node).setPromptText(bundle.getString(node.getId()));
                        } else if (node instanceof Button) {
                            ((Button) node).setText(bundle.getString(node.getId()));
                        }else if(node instanceof MenuButton) {
                            ((MenuButton) node).setText(bundle.getString(node.getId()));
                        }else if(node instanceof Label){

                                ((Label) node).setText(bundle.getString(node.getId()));
                        }else if(node instanceof DatePicker){
                            ((DatePicker) node).setPromptText(bundle.getString(node.getId()));
                        }else if(node instanceof TableView<?>){
                            TableView<?> tableView = (TableView<?>) node;

                            for (TableColumn<?, ?> column : tableView.getColumns()) {
                                column.setText(bundle.getString(column.getId()));
                            }

                        }
                    }
                }
            }catch (NullPointerException e) {
            }

        }


}
