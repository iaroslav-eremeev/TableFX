module com.iaroslaveremeev.tablefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.iaroslaveremeev.tablefx to javafx.fxml;
    exports com.iaroslaveremeev.tablefx;
}