package com.iaroslaveremeev.tablefx.conrollers;

import com.iaroslaveremeev.tablefx.util.MailSender;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class SendMailController {
    public TextField mailSubject;
    public TextArea messageText;

    public void initialize(){
        this.messageText.setWrapText(true);
    }
    public void send(ActionEvent actionEvent) {
        MailSender mailSender = new MailSender(Preferences.userRoot().node("mail").get("senderEmail", null),
                Preferences.userRoot().node("mail").get("password", null),
                Preferences.userRoot().node("mail").get("recipientEmail", null));
        boolean sentSuccessfully = mailSender.send(this.mailSubject.getText(), this.messageText.getText());
        if (sentSuccessfully) {
            Alert success = new Alert(Alert.AlertType.INFORMATION, "The message is sent successfully!");
            success.show();
            Preferences.userRoot().node("mail").putBoolean("sent", true);
            Stage stage = (Stage) this.mailSubject.getScene().getWindow();
            stage.close();
        }
    }
}
