package app;

import controller.BGmain;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Navigator {

    private static Stack<Scene> sceneStack = new Stack<>();
    public final static String LOGIN_PAGE = "login.fxml";
    public final static String HOME_PAGE = "dashboard.fxml";
    public final static String COMPANY_PAGE = "company.fxml";
    public final static String SEARCH_COMPANY_PAGE = "searchCompany.fxml";
    public final static String ADD_BUS_PAGE = "addBus.fxml";
    public final static String ADD_BUS_LINE = "RegisterLine.fxml";
    public final static String REGISTER_PAGE = "register.fxml";

    public final static String CREATE_COMPAMY = "createCompany.fxml";

    public static void navigate(Stage stage, String page){
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(page)
        );
        try{
            Scene scene = new Scene(loader.load());
            if (stage.getScene() != null){
            sceneStack.push(stage.getScene());
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static void navigate(Event event, String page) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        navigate(stage, page);
    }

    public static void goBack(ActionEvent ae) {
        if (!sceneStack.isEmpty()) {
            Scene previousScene = sceneStack.pop();
            Node node = (Node) ae.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        }
    }

    public static Stack<Scene> getSceneStack() {
        return sceneStack;
    }
}