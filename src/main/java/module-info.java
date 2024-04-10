module com.example.dndictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires freetts;
    requires java.net.http;
    requires org.controlsfx.controls;
    requires com.google.gson;

    opens com.example.dndictionary to javafx.fxml, com.google.gson;
    exports com.example.dndictionary;
    opens com.example.dndictionary.controllers to javafx.fxml, com.google.gson;
    exports com.example.dndictionary.controllers;
}