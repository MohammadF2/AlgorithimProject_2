module com.example.algorithimproject_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.algorithimproject_2 to javafx.fxml;
    exports com.example.algorithimproject_2;
}