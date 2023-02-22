module com.iaroslaveremeev.tablefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javax.mail.api;

    opens com.iaroslaveremeev.tablefx to javafx.fxml;
    exports com.iaroslaveremeev.tablefx;
}