module com.example.dndictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dndictionary to javafx.fxml;
    exports com.example.dndictionary;
}