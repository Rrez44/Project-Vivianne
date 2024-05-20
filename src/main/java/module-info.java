module Vivianne {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.xml.dom;

    exports app;
    opens controller to javafx.fxml;
}