package com.iaroslaveremeev.tablefx;

import com.iaroslaveremeev.tablefx.util.MailSender;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.prefs.Preferences;

public class SendMailController {

    //TODO Добавить вычитку электронной почты из таблицы! (Preferences)
    //TODO Проверить textArea - при табуляции уходит в бесконечность вправо
    public TextField mailSubject;
    public TextArea messageText;
    public TextField password;

    public void initialize(){
    }
    public void send(ActionEvent actionEvent) {
        MailSender mailSender = new MailSender("tirsbox@mail.ru",
                this.password.getText(), Preferences.userRoot().get("mail", null));
        mailSender.send(this.mailSubject.getText(), this.messageText.getText());
    }
}
