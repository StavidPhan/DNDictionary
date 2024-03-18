module com.example.dndictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dndictionary to javafx.fxml;
    exports com.example.dndictionary;
    exports com.example.dndictionary.controllers;
    opens com.example.dndictionary.controllers to javafx.fxml;
}