

//  DEPRECATED

//package app;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.web.WebView;
//import javafx.stage.Stage;
//import java.nio.file.Paths;
//
//public class Map extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        WebView webView = new WebView();
//
//        String path = Paths.get("C:/Projects/Project-Vivianne-main/src/main/java/app/map.html").toUri().toString();
//        webView.getEngine().load(path);
//
//        Scene scene = new Scene(webView, 800, 500);
//        primaryStage.setTitle("JavaFX WebView Example");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
