package service;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public class Translate {

    private static final StringProperty language = new SimpleStringProperty("English");

    public static StringProperty languageProperty(){
        return language;
    }

    public static String getLanguage(){
        return language.get();
    }

    public static void setLanguage(String lang) {
        language.set(lang);
    }


    public static void translateForAllPanes(Pane pane){
        TranslateRecords.translateFormInputs(Translate.getLanguage(),pane);
        Translate.languageProperty().addListener((observable, oldValue, newValue) ->TranslateRecords.translateFormInputs(Translate.getLanguage(),pane));

    }


}
