package com.iaroslaveremeev.tablefx.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaroslaveremeev.tablefx.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
            SendUserIdRepository sendUserIdRepository = new SendUserIdRepository();
            for (User user : this.users) {
                if (sendUserIdRepository.userIds.contains(user.getId())) {
                    user.setSent(true);
                }
            }
        }
        catch (IOException ignored){};
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void fill(int number){
        String[] names = new String[]{"Jack", "Michael", "Mary", "Andrew", "Jane", "Elizabeth", "Nick", "Kate", "Paul", "Margaret"};
        String[] countries = new String[]{"UK", "USA", "Australia", "New Zealand"};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int nameIndex = random.nextInt(names.length);
            int countryIndex = random.nextInt(countries.length);
            String userName = names[nameIndex];
            User user = new User(i + 1, userName, userName + random.nextInt(100) + "@mymail.com", random.nextInt(50 - 18) + 18,
                    countries[countryIndex]);
            this.users.add(user);
        }
    }
}
