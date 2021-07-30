package com.telegrambots.firstcook.service;


import com.telegrambots.firstcook.model.tUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UsersProfileMongoRepositoryImpl {
    @Autowired
    private UsersProfileMongoRepository UsersProfileMongoRepository;

    public void addUserForDB(tUser s) {
        UsersProfileMongoRepository.save(s);
    }

    public List<tUser> getAllUserForDB(String Chat_id) {
        return   UsersProfileMongoRepository.findAllById(Chat_id)==null?
                Collections.emptyList()
                :UsersProfileMongoRepository.findAllById(Chat_id);

    }

    public String toStringTagAll(List<tUser> tUserList) {
        StringBuilder alluser = new StringBuilder();
        for (tUser t : tUserList) {
            alluser.append(t.getUsername());

        }
        return alluser.toString();
    }
}
