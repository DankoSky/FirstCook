package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.User;
import com.telegrambots.firstcook.repository.UserPostgreRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@NoArgsConstructor
public class UserServiceImpl {
    private UserPostgreRepository repository;

    @Autowired
    public UserServiceImpl(UserPostgreRepository repository) {
        this.repository = repository;
    }


    public SendMessage getAllUserForDB(Update update) {
        String chat_id = update.getMessage().getChatId().toString();

        List<User> temp = repository.findAll();
        StringBuilder s = new StringBuilder();
        for (User User : temp) {
            if (User.chat_id.equals(chat_id)) {
                s.append(User.username).append(", ");
            }
        }

        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Ага, вот эти ребята: " + s);
        return message;

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

}
