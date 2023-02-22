package com.iaroslaveremeev.tablefx;

import com.iaroslaveremeev.tablefx.util.MailSender;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SendMailController {

    //TODO Добавить вычитку электронной почты из таблицы! (Preferences)
    //TODO Проверить textArea - при табуляции уходит в бесконечность вправо
    public TextField mailSubject;
    public TextArea messageText;

    public void initialize(){
    }

    public void send(ActionEvent actionEvent) {
        MailSender mailSender = new MailSender("tirsbox@mail.ru",
                "", "eremeev.pt@gmail.com");
        mailSender.send(this.mailSubject.getText(), this.messageText.getText());
    }
}
