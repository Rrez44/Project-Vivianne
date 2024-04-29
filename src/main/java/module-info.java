module Vivianne {
    requires javafx.controls;
    requires javafx.fxml;

    exports app;
    opens controller to javafx.fxml;
}