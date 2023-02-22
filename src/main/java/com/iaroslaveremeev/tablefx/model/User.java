package com.iaroslaveremeev.tablefx.model;

import com.iaroslaveremeev.tablefx.repository.UserRepository;
import com.iaroslaveremeev.tablefx.util.Constants;

import java.io.File;
import java.util.Date;
import java.util.Objects;

public class User {

    /**
     * 1.	Создать модель данных User с полями: идентификатор (целое число),
     * имя пользователя, дата регистрации (dd.MM.yyyy HH:mm:ss), электронная почта, возраст, страна, отправлено ли письмо
     * (название поля: isSend - true/false, исключить данное поле из json, как на загрузку, так и на выгрузку)
     */
    private int id;
    private String name;
    private Date regDate;
    private String mail;
    private int age;
    private String country;
    private boolean isSent;

    public User() {
    }

    public User(int id, String name, String mail, int age, String country) {
        this.id = id;
        this.name = name;
        this.regDate = new Date();
        this.mail = mail;
        this.age = age;
        this.country = country;
        this.isSent = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && isSent == user.isSent && Objects.equals(name, user.name) && Objects.equals(regDate, user.regDate) && Objects.equals(mail, user.mail) && Objects.equals(country, user.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, regDate, mail, age, country, isSent);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regDate=" + regDate +
                ", mail='" + mail + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", isSent=" + isSent +
                '}';
    }
}
