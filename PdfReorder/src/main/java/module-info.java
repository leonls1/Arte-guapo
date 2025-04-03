module com.leon.pdfreorder {
    requires javafx.controls;
    requires javafx.fxml;
    requires tabula;
    requires org.apache.pdfbox;

    opens com.leon.pdfreorder to javafx.fxml;
    opens com.leon.pdfreorder.controller to javafx.fxml;
    opens com.leon.pdfreorder.service to javafx.fmxl;
    opens com.leon.pdfreorder.utils to javafx.fxml;
    exports com.leon.pdfreorder;
}
