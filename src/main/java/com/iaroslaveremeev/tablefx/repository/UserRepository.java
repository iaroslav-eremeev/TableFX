package com.iaroslaveremeev.tablefx.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.tablefx.model.User;
import com.iaroslaveremeev.tablefx.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserRepository {

    ArrayList<User> users = new ArrayList<>();

    /**
     * 3.	Создать репозиторий, конструктор которого принимает на вход объект типа File
     * и производит инициализацию списка пользователей из этого файла.
     * Далее в этом конструкторе проставить для каждого объекта в поле isSend значение true,
     * если его id есть в списке репозитория SendUserIdRepository
     */

    public UserRepository(File fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            this.users = objectMapper.readValue(bufferedReader, new TypeReference<>() {});
        }
        catch (IOException ignored){};
    }
}
