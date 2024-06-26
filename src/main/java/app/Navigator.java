package app;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.SimpleTimeZone;
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
    public final static String SEARCH_BUS = "searchBus.fxml";
    public final static String MANAGE_BUS = "manageBus.fxml";
    public final static String STATISTICS = "statistics.fxml";
    public final static String CREATE_LINE = "CreateLine.fxml";

    public final static String GET_USERS ="getUsers.fxml";
    public final static String PROFILE_PAGE = "profile.fxml";

    public final static String MANAGE_LINE = "manageBusLine.fxml";


    public static boolean firstNav = true;
    public static boolean loginNav =true;

    public static void navigate(Stage stage, String page){
        FXMLLoader loader = new FXMLLoader(
                Navigator.class.getResource(page)
        );
        try{
            Scene scene = new Scene(loader.load());

            if(firstNav){
                firstNav = false;
            }else{
                if (loginNav) {
                    loginNav =false;
                }else {
                    sceneStack.push(stage.getScene());
                }
            }

            stage.getIcons().add(new Image(Objects.requireNonNull(Navigator.class.getResourceAsStream("/Images/icon.png"))));
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
