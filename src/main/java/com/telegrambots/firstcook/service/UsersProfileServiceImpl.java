package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.tUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsersProfileServiceImpl {
    private UsersProfileRepository UsersProfileRepository;
    private UsersProfileServiceImpl systemBot;

    public void addUserForDB(tUser s) {
        UsersProfileRepository.save(s);
    }

    public String getAllUserForDB(String Chat_id) {
        List<tUser> temp = UsersProfileRepository.findAll();
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
