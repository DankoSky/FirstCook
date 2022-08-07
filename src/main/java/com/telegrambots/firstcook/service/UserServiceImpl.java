package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.User;
import com.telegrambots.firstcook.repository.UserPostgreRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.List;

@Service
@NoArgsConstructor
public class UserServiceImpl {
    private UserPostgreRepository repository;

    @Autowired
    public UserServiceImpl(UserPostgreRepository repository) {
        this.repository = repository;
    }


    public String getAllUserForDB(String  chat_id) {
        List<User> temp = repository.findAll();
        StringBuilder s = new StringBuilder();
        for (User User : temp) {
            if (User.chat_id.equals(chat_id)) {
                s.append(User.username).append(", ");
            }
        }
        return s.toString();
    }

    public String getAllUsersAndBirthday(String chat_id) {
        List<User> temp = repository.findAll();
        StringBuilder s = new StringBuilder();
        for (User User : temp) {
            if (User.chat_id.equals(chat_id)) {
                s.append(User.username).append(" : ").append(User.birthday).append("\n");
            }
        }
        return s.toString();
    }

    public User getUserByUsername(String username) {
        List<User> list = repository.findAll();
        for (User tuser : list) {
            if (tuser.username.equals(username)) {
                return tuser;
            }
        }
        return new User();
    }

    public String checkBirthday(LocalDate localDate){
        List<User> temp = repository.findAll();
        for (User user : temp) {
            int day = user.getBirthday().getDayOfMonth();
            int month = user.getBirthday().getMonthValue();
            if(localDate.getMonthValue() == month && localDate.getDayOfMonth() == day ){
                return "Сегодня день рождения: " + user.getUsername();

            }
        }
        return "nope";

    }

}
