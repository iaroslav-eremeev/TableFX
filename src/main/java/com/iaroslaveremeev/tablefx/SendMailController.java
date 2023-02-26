package com.iaroslaveremeev.tablefx;

import com.iaroslaveremeev.tablefx.util.MailSender;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class SendMailController {

    //TODO Проверить textArea - при табуляции уходит в бесконечность вправо
    //TODO Галочка не ставится
    public TextField mailSubject;
    public TextArea messageText;
    public PasswordField password;

    public void initialize(){
        this.messageText.setWrapText(true);
    }
    public void send(ActionEvent actionEvent) {
        MailSender mailSender = new MailSender("tirsbox@mail.ru",
                this.password.getText(), Preferences.userRoot().node("mail").get("mail", null));
        boolean sentSuccessfully = mailSender.send(this.mailSubject.getText(), this.messageText.getText());
        if (sentSuccessfully) {
            Preferences.userRoot().node("mail").putBoolean("sent", true);
            Stage stage = (Stage) this.mailSubject.getScene().getWindow();
            stage.close();
        }
    }
}
