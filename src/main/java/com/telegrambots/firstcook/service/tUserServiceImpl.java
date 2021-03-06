package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.Role;
import com.telegrambots.firstcook.model.User;
import com.telegrambots.firstcook.repository.UserProfilePostgreRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@NoArgsConstructor
public class tUserServiceImpl {

    private UserProfilePostgreRepository repository;


    @Autowired
    public tUserServiceImpl(UserProfilePostgreRepository repository) {
        this.repository = repository;
    }


    public void addUserForDB(User s) {
        repository.save(s);
    }

    public String getAllUserForDB(String chat_id) {
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

    public String setBirthday(String InputStringCommandADR) {
        String username = InputStringCommandADR.substring(4, InputStringCommandADR.length() - 10).trim();
        Calendar date = CheckingConvertingInputStringDateFromChat(InputStringCommandADR);
        String AnswerForChat = "???????????????? ???????????? ????????";

        if (getUserByUsername(username).username == null) {
            AnswerForChat = "???????????? ?????????? ?????? ?????? ?? ????";
        } else {
            List<User> list = repository.findAll();
            for (User tuser : list) {
                if (tuser.username.equals(username)) {
                    tuser.setBirthday(date);
                    repository.save(tuser);
                    AnswerForChat = "???????????????? ????, ??????";
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
                return "???????????? " + tuser.username;
            }
        }
        return "???? ?????????????? ??????????: " + username;
    }

    public Calendar CheckingConvertingInputStringDateFromChat(String ChekingDate) {
        String date = ChekingDate.substring((ChekingDate.length() - 10));
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));
        boolean AnswerForChat = date.length() == 10 &&
                day >= 0 && day <= 32 &&
                month >= 0 && month <= 13 &&
                year >= 1950 && year <= 2022; // ???????????????? ???????? ???? ???????????? ???????????? ??????????.

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
