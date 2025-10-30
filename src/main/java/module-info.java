module com.ezrachai {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ezrachai to javafx.fxml;
    exports com.ezrachai;
}
