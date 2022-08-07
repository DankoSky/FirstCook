package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.Role;
import com.telegrambots.firstcook.model.User;
import com.telegrambots.firstcook.repository.UserProfilePostgreRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@NoArgsConstructor
public class UserServiceImpl {

    private UserProfilePostgreRepository repository;


    @Autowired
    public UserServiceImpl(UserProfilePostgreRepository repository) {
        this.repository = repository;
    }


    public SendMessage addUserForDB(Update update) {
        SendMessage message = new SendMessage();
        String textMessage = update.getMessage().getText().toLowerCase();
        String chat_id = update.getMessage().getChatId().toString();

        User user = new User();
        user.setUsername(textMessage.substring(4).trim());
        user.setChat_id(chat_id);
        user.setIsAdmin(Role.USER);
        repository.save(user);
        message.setText("Записала, шеф");
        return message;
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

    public String setBirthday(String InputStringCommandADR) {
        String username = InputStringCommandADR.substring(4, InputStringCommandADR.length() - 10).trim();
        Calendar date = CheckingConvertingInputStringDateFromChat(InputStringCommandADR);
        String AnswerForChat = "Неверный формат даты";

        if (getUserByUsername(username).username == null) {
            AnswerForChat = "Такого юзера еще нет в БД";
        } else {
            List<User> list = repository.findAll();
            for (User tuser : list) {
                if (tuser.username.equals(username)) {
                    tuser.setBirthday(date);
                    repository.save(tuser);
                    AnswerForChat = "Записала др, шеф";
                }
            }
        }
        return AnswerForChat;
    }

    public boolean setAdmin(String username) {
        List<User> list = repository.findAll();
        for (User tuser : list) {
            if (tuser.username.equals(username)) {
                tuser.setIsAdmin(Role.ADMIN);
                repository.save(tuser);
                return true;
            }
        }
        return false;
    }

    public String deleteByUserName(String username) {
        List<User> list = repository.findAll();
        for (User tuser : list) {
            if (tuser.username.equals(username)) {
                repository.delete(tuser);
                return "Удален " + tuser.username;
            }
        }
        return "Не удалось найти: " + username;
    }

    public Calendar CheckingConvertingInputStringDateFromChat(String ChekingDate) {
        String date = ChekingDate.substring((ChekingDate.length() - 10));
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        boolean AnswerForChat = date.length() == 10 &&
                day >= 0 && day <= 32 &&
                month >= 0 && month <= 13 &&
                year >= 1950 && year <= 2022; // Проверяю дату на верный формат ввода.

        Calendar UserBirthday = new GregorianCalendar();
        if (AnswerForChat) {
            UserBirthday.set(Calendar.DAY_OF_MONTH, day);
            UserBirthday.set(Calendar.MONTH, month);
            UserBirthday.set(Calendar.YEAR, year);
//            UserBirthday.set(Calendar. year);
//            UserBirthday.set(Calendar.YEAR, year);
//            UserBirthday.set(Calendar.YEAR, year);

        }
        return UserBirthday;
    }
}
