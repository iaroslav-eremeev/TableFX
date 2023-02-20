package com.iaroslaveremeev.tablefx.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.tablefx.util.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SendUserIdRepository {

    /**
     * 2.	Создать репозиторий SendUserIdRepository, конструктор которого не принимает на вход аргументов,
     * производит загрузку данных в список целых чисел из файла send.json.
     * Имя данного файла сделать статической константой в классе Constants пакета util.
     */
    ArrayList<Integer> userIds = new ArrayList<>();

    public SendUserIdRepository() {
        ObjectMapper objectMapper = new ObjectMapper();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Constants.FILENAME))){
            this.userIds = objectMapper.readValue(bufferedReader, new TypeReference<>() {});
        } catch (IOException ignored) {}
    }
}
