package controller;

import app.Navigator;
import javafx.event.ActionEvent;

public abstract class BGmain {
    public void handleDashboard(ActionEvent event){
//        System.out.println("Hello");
        Navigator.navigate(event,Navigator.HOME_PAGE);
    }

    public void handleCreateLine(ActionEvent event){
        Navigator.navigate(event,Navigator.ADD_BUS_LINE);
    }

    public void handleReport(ActionEvent event){
        Navigator.navigate(event,Navigator.COMPANY_PAGE);
    }

    public void handleRegister(ActionEvent event){
        Navigator.navigate(event,Navigator.REGISTER_PAGE);
    }

    public void handleLogOut(ActionEvent event){
        Navigator.navigate(event,Navigator.LOGIN_PAGE);
    }
}
