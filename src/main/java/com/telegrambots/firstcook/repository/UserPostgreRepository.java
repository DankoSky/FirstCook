package com.telegrambots.firstcook.repository;

import com.telegrambots.firstcook.model.Role;
import com.telegrambots.firstcook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserPostgreRepository {
    public List<User> findAll() {
        return List.of(User.builder()
                .birthday(LocalDate.of(2002, 1, 1))
                .id(0)
                .chat_id("1")
                .username("Pezduk usatiy")
                .isAdmin(Role.USER)
                .build());
    }
}
