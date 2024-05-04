package app;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {
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




}