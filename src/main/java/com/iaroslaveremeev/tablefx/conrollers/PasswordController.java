package com.iaroslaveremeev.tablefx.conrollers;

import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class PasswordController {
    public PasswordField password;

    public void initialize(){
        Preferences.userRoot().node("mail").put("password", this.password.getText());
        Stage passStage = (Stage) this.password.getScene().getWindow();
        passStage.close();
    }
}
