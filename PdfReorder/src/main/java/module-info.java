module com.leon.pdfreorder {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.leon.pdfreorder to javafx.fxml;
    opens com.leon.pdfreorder.controller to javafx.fxml;
    exports com.leon.pdfreorder;
}
