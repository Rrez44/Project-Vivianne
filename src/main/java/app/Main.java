package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage){
        Navigator.navigate(stage,Navigator.HOME_PAGE);
    }

}