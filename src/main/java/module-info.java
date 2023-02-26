module com.iaroslaveremeev.tablefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javax.mail.api;
    requires java.prefs;

    opens com.iaroslaveremeev.tablefx to javafx.fxml;
    opens com.iaroslaveremeev.tablefx.model to javafx.base;
    exports com.iaroslaveremeev.tablefx;
    exports com.iaroslaveremeev.tablefx.model to com.fasterxml.jackson.databind;
    exports com.iaroslaveremeev.tablefx.repository to com.fasterxml.jackson.databind;
    exports com.iaroslaveremeev.tablefx.conrollers;
    opens com.iaroslaveremeev.tablefx.conrollers to javafx.fxml;
}