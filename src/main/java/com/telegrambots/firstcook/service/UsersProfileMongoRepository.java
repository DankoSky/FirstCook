package com.telegrambots.firstcook.service;

import com.telegrambots.firstcook.model.tUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersProfileMongoRepository extends MongoRepository<tUser, String> {

        List<tUser> findAllById(String chat_id);
        void deleteByUsername(String username);
  //  void addyUserDb(tUser user);
//    void deletetUserByUsername(String username);
//    void deleteAllByChat_id(Long Chat_id);
}
