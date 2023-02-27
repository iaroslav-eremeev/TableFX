package com.iaroslaveremeev.tablefx.conrollers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class PasswordController {
    public TextField senderEmail;
    public PasswordField password;

    public void enterPassword(ActionEvent actionEvent) {
        Preferences.userRoot().node("mail").put("senderEmail", this.senderEmail.getText());
        Preferences.userRoot().node("mail").put("password", this.password.getText());
        Stage passStage = (Stage) this.senderEmail.getScene().getWindow();
        passStage.close();
    }
}
