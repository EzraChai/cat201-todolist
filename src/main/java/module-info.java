module com.ezrachai {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.tri to javafx.fxml, com.fasterxml.jackson.databind;

    exports com.tri;
}
