package com.telegrambots.firstcook.service;

import com.telegrambots.firstcook.model.tUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersProfileRepository extends JpaRepository<tUser, String> {

        List<tUser> findAllById(String chat_id);
        void deleteByUsername(String username);
      //  List<tUser> findAllBychat_id(String chat_id);
  //  void addyUserDb(tUser user);
//    void deletetUserByUsername(String username);
//    void deleteAllByChat_id(Long Chat_id);
}
