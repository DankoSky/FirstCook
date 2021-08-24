package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.tUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsersProfileMongoRepositoryImpl {
    private UsersProfileMongoRepository UsersProfileMongoRepository;
    private UsersProfileMongoRepositoryImpl systemBot;

    public void addUserForDB(tUser s) {
        UsersProfileMongoRepository.save(s);
    }

    public String getAllUserForDB(String Chat_id) {
        List<tUser> temp = UsersProfileMongoRepository.findAll();
        ArrayList<tUser> temp2 = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        for (com.telegrambots.firstcook.model.tUser tUser : temp) {
            if (tUser.chat_id.equals(Chat_id)) {
                s.append(tUser.username).append(", ");
            }
        }
        return s.toString();
    }
}
