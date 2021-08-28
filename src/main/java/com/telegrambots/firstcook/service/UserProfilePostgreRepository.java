package com.telegrambots.firstcook.service;

import com.telegrambots.firstcook.model.tUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfilePostgreRepository extends JpaRepository<tUser,String> {
    List<tUser> findAllById(String chat_id);
    void deleteByUsername(String username);
}
