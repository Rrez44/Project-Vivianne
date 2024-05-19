package otherFunctionality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class AddTime {


    public static void addHour(MenuButton menuButton, EventHandler<ActionEvent> event){

        for(int i=5;i<=20;i++){
            MenuItem item = new MenuItem();
            if(i<10) {
                item.setText("0"+i + "");
            }else{
                item.setText(i + "");
            }
            item.setOnAction(event);
            menuButton.getItems().add(item);
        }


    }

    public static void addMinutes(MenuButton menuButton,EventHandler<ActionEvent> event){

        for(int i=0;i<=45;i+=15){
            MenuItem item = new MenuItem();
            if(i ==0 ) {
                item.setText(i + "0");
            }else{
                item.setText(i + "");
            }

            item.setOnAction(event);
            menuButton.getItems().add(item);
        }


    }



}
