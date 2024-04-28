package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class login extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene sc = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(sc);
        stage.show();

    }
}
