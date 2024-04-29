module Vivianne {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports app;
    opens controller to javafx.fxml;
}