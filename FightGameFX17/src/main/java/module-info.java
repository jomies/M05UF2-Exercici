module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens application to javafx.fxml;
    opens application.controller to javafx.fxml;

    exports application.controller;
    exports application;
}