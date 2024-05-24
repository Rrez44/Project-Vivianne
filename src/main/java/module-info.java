module Vivianne {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.xml.dom;
    requires javafx.base;

    exports app;
    opens controller to javafx.fxml ,javafx.base;
//    opens Vivianne to javafx.base;
    opens model to javafx.fxml ,javafx.base;

}