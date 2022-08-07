package com.telegrambots.firstcook.repository;

import com.telegrambots.firstcook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilePostgreRepository extends JpaRepository<User, String> {
}
