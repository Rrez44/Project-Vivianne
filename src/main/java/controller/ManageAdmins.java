package controller;

import ENUMS.Role;
import app.Session;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.TableCell;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.User;
import model.dto.UserDto;
import repository.UserRepository;
import service.PasswordHasher;
import service.Translate;
import service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageAdmins extends BGmain implements Initializable {

    @FXML
    private TableView<User> tblGetUser;

    @FXML
    private TableColumn<User, String> tblFirstName;

    @FXML
    private TableColumn<User, String> tblLastName;

    @FXML
    private TableColumn<User, String> tblUsername;

    @FXML
    private TableColumn<User, String> tblEmail;

    @FXML
    private TableColumn<User, String> tblStatus;

    @FXML
    private TableColumn<User, String> tblRemove;

    @FXML
    private Pane paneTableView;

    private final ObservableList<User> data = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Translate.translateForAllPanes(paneTableView);
        tblUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("role"));
        tblRemove.setCellFactory(column -> new TableCell<User, String>() {
            private final Button button = new Button("Delete");


            {
                button.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    UserService.deleteUser(user.getUsername());
                    tblGetUser.getItems().remove(getIndex());
                });

            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });


        data.addAll(UserService.getAllUsers(Session.getUser().getUsername()));

        tblUsername.setResizable(false);
        tblEmail.setResizable(false);
        tblStatus.setResizable(false);
        tblRemove.setResizable(false);


        tblGetUser.setItems(data);


    }

    }
