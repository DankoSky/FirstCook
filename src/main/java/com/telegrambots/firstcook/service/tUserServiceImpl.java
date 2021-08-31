package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.Role;
import com.telegrambots.firstcook.model.tUser;
import com.telegrambots.firstcook.repository.UserProfilePostgreRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class tUserServiceImpl {

    private UserProfilePostgreRepository repository;


    @Autowired
    public tUserServiceImpl(UserProfilePostgreRepository repository) {
        this.repository = repository;
    }




    public void addUserForDB(tUser s) {
        repository.save(s);
    }

    public String getAllUserForDB(String chat_id) {
        List<tUser> temp = repository.findAll();
        StringBuilder s = new StringBuilder();
        for (tUser tUser : temp) {
            if (tUser.chat_id.equals(chat_id)) {
                s.append(tUser.username).append(", ");
            }
        }
        return s.toString();
    }

    public String getAllUsersAndBirthday(String chat_id) {
        List<tUser> temp = repository.findAll();
        StringBuilder s = new StringBuilder();
        for (tUser tUser : temp) {
            if (tUser.chat_id.equals(chat_id)) {
                s.append(tUser.username).append(" : ").append(tUser.birthday).append("\n");
            }
        }
        return s.toString();
    }

    public tUser getUserByUsername(String username) {
        List<tUser> list = repository.findAll();
        for (tUser tuser : list) {
            if (tuser.username.equals(username)) {
                return tuser;
            }
        }
        return new tUser();
    }

    public tUser setBirthday(String username, String date) {
        List<tUser> list = repository.findAll();
        for (tUser tuser : list) {
            if (tuser.username.equals(username)) {
                tuser.setBirthday(date);
                repository.save(tuser);
                return tuser;
            }
        }
        return new tUser();
    }

    public boolean setAdmin(String username) {
        List<tUser> list = repository.findAll();
        for (tUser tuser : list) {
            if (tuser.username.equals(username)) {
                tuser.setIsAdmin(Role.ADMIN);
                repository.save(tuser);
                return true;
            }
        }
        return false;
    }

    public String deleteByUserName(String username){
        List<tUser> list = repository.findAll();
        for (tUser tuser : list) {
            if (tuser.username.equals(username)) {
                repository.delete(tuser);
                return "Удален" + tuser.username;
            }
        }
       return "Не удалось найти: " + username;
    }
}
